package com.tblog.controller;

import com.tblog.common.Constants;
import com.tblog.common.MD5Utils;
import com.tblog.entity.User;
import com.tblog.mail.SendEmail;
import com.tblog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tyc
 * @date 2019/5/10
 */
@Controller
@RequestMapping("/**/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    private final static Logger logger = Logger.getLogger(UserController.class);


    @RequestMapping(value = "/testRedirect")
    public String redirect(Model model) {
        System.out.println("test redurect!@@@@@@@!");
//        ModelAndView mav = new ModelAndView("redirect:/testPage.jsp");

        return "/regist/registerSuccess";
    }

//    @RequestMapping(value = "/login",method = RequestMethod.GET)
////    public String login(){
////        logger.info("进入登录页面aaz.a.");
////        return "/login";
////    }

    /**
     * 判断是否登录
     * @param model
     * @return
     */
    @RequestMapping(value = "/judgeLogin",method = RequestMethod.GET)
    public String judgeLogin(Model model){
        User user = (User) getSession().getAttribute("user");
        if (user!=null){
            return "/personal/personal";
        }
        return "../login";
    }

    /**
     * 账号密码登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/normalLogin", method = RequestMethod.POST)
    public String normalLogin(Model model,
                              @RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value = "code", required = false) String code) {


        if (StringUtils.isBlank(code)) {
            model.addAttribute("error", "fail");
            return "../login";
        }

        //校验验证码是否正确
        if (checkValidateCode(code) != 1) {
            model.addAttribute("error", "fail");
            return "../login";
        }
        password = MD5Utils.encodeToHex(password + Constants.SALT);
        User user = userService.login(username, password);

//        System.out.println("user.getStatus:"+user.getState());
        if (user != null) {
            if ("0".equals(user.getState())) {

                //未激活
                model.addAttribute("email",username);
                model.addAttribute("error","active");
                return "../login";


//                model.addAttribute("email", username);
//                model.addAttribute("error", "active");
//                return "redirect:../login.jsp/{email}";
            }
            logger.info("用户使用 账号密码 登录成功.");
            model.addAttribute("user", user);
            getSession().setAttribute("user",user);
            return "/personal/personal";
        } else {
            logger.info("用户使用 账号密码 登录失败.");
            model.addAttribute("error", "fail");
            model.addAttribute("email", username);
//            return "redirect:/login.jsp";
            return "../login";
        }

    }


    /**
     * 账号激活
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activate(Model model) {

        logger.info("=============激活验证=============");

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String email = attr.getRequest().getParameter("email");
        String validateCode = attr.getRequest().getParameter("validateCode");
        String redisCode = redisTemplate.opsForValue().get(email);
        logger.info("验证邮箱: " + email + " , 邮箱激活码: " + redisCode + " , 用户链接激活码: " + validateCode);

        //判断是否已经激活
        User user = userService.findByEmail(email);
        if (user != null && "1".equals(user.getState())) {
            logger.info("=============账号:" + email + " 已经激活=============");
            model.addAttribute("success", "您的账号已经激活,请登录.");
//            return "../login";
            return "user/login";
        } else if (redisCode == null) {
            logger.info("=============激活码:" + redisCode + "过期了=============");
            //激活码过期
            model.addAttribute("fail", "您的激活码已失效,请重新注册.");
            userService.deleteByEmail(email);
            return "/regist/activeFail";

        } else if (StringUtils.isNotBlank(validateCode) && validateCode.equals(redisCode)) {
            logger.info("=============激活码正确=============");
            //激活码正确
            user.setState("1");
            user.setEnable("1");
            userService.update(user);
            model.addAttribute("email", email);
            return "/regist/activeSuccess";
        } else {
            logger.info("=============激活码错误=============");
            model.addAttribute("fail", "激活码错误,请重新激活.");
            return "/regist/activeFail";
        }
    }

    /**
     * 重新发送激活邮件
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendEmail(Model model) {
        Map map = new HashMap<String, Object>();
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String email = attr.getRequest().getParameter("email");
        String validateCode = attr.getRequest().getParameter("validateCode");
        SendEmail.sendEmailMessage(email, validateCode);
        map.put("message", "success");

        return map;
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "password", required = true) String password,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "nickName", required = false) String nickName,
                           @RequestParam(value = "code", required = false) String code) {

        logger.debug("注册...");
        if (StringUtils.isBlank(code)) {
            model.addAttribute("error", "非法注册,请重新注册!");
            return "../register";
        }
        int b = checkValidateCode(code);

        /**
         * TODO: 2019/7/26
         *  error 后可能应该跟 fail
         */

        if (b == -1) {
            model.addAttribute("error", "验证码超时,请重新注册!");
            return "../register";
        } else if (b == 0) {
            model.addAttribute("error", "验证码错误,请重新输入!");
            return "../register";
        }


        /**
         *  TODO: 2019/6/22
         *   不验证 email 是否重复
         *   验证则需要将以下四行互换
         */
        User user;
        if (false) {
            //User user = userService.findByEmail(email);
            //if (user != null) {


            model.addAttribute("error", "该邮箱已经注册,请登录或更换邮箱后注册!");
            return "../register";
        } else {
            user = new User();


            user.setEmail(email);
            user.setNickName(nickName);
            user.setPassword(MD5Utils.encodeToHex(password + Constants.SALT));
            user.setEnable("0");
            user.setState("0");
            user.setPhone(phone);
            user.setImgUrl("/images/icon_m.jpg");

            //邮件激活码
            String validateCode = MD5Utils.encodeToHex(email + System.currentTimeMillis());
            //1小时内激活有效
            redisTemplate.opsForValue().set(email, validateCode, 1, TimeUnit.HOURS);

            userService.regist(user);

            logger.info("注册成功...");

            SendEmail.sendEmailMessage(email, validateCode);

            String message = email + "," + validateCode;
            model.addAttribute("message", message);
            return "/regist/registerSuccess";

        }

    }

    /**
     * 校验激活码
     * -1 : 验证码为null
     * 0 : 验证码错误
     * 1 : 验证码正确
     *
     * @param code
     * @return
     */
    private int checkValidateCode(String code) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object captcha = attrs.getRequest().getSession().getAttribute("captcha");
        if (captcha == null) {
            return -1;
        } else if (!code.equalsIgnoreCase(captcha.toString())) {
            return 0;
        } else {
            return 1;
        }
    }


    @RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkPhone(Model model, @RequestParam(value = "phone", required = false) String phone) {
        logger.info("注册---判断手机号" + phone + "是否可用");
        Map map = new HashMap<String, Object>();
        User user = userService.findByPhone(phone);

        if (user == null) {
            map.put("message", "success");
        } else {
            map.put("message", "fail");
        }
        return map;
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkEmail(Model model, @RequestParam(value = "email", required = false) String email) {
        logger.info("注册---判断邮箱" + email + "是否可用");
        Map map = new HashMap<String, Object>();

        /**
         *  TODO: 2019/6/24
         *   不验证 email 是否重复
         *   验证则需要将以下两行互换
         */


        User user = null;
//        User user = userService.findByEmail(email);


        if (user == null) {
            map.put("message", "success");
        } else {

            /**
             * TODO: 2019/6/22
             *  不验证 email 是否重复 ,
             *  验证则需要将以下两行互换
             *  即使用  map.put("message", "success");
             */

//            map.put("message", "fail");
            map.put("message", "success");
        }
        return map;
    }


    @RequestMapping("/checkCode")
    @ResponseBody
    public Map<String, Object> checkCode(Model model, @RequestParam(value = "code", required = false) String code) {

        logger.info("---判断验证码" + code + "是否正确");
        Map map = new HashMap<String, Object>();
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String captcha = (String) attrs.getRequest().getSession().getAttribute("captcha");


        if (code.equalsIgnoreCase(captcha)) {
            map.put("message", "success");
            System.out.println("验证码正确");
        } else {
            map.put("message", "fail");
            System.out.println("验证码错误   captcha: " + captcha+"   code : "+code);

        }

        return map;
    }


//    @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
//    public Map<String, Object> checkCode(Model model, @RequestParam(value = "code", required = false) String code) {
//        logger.debug("注册-判断验证码" + code + "是否正确.");
////        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////        String vcode = (String) attr.getRequest().getSession().getAttribute(CodeCaptchaServlet.VERCODE_KEY);
//
//
//
//        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest req = attrs.getRequest();
//        HttpSession session = req.getSession();
//
//        Object obj =  session.getAttribute(CodeCaptchaServlet.VERCODE_KEY);
//        if (obj == null){
//            System.out.println("obj==null");
//        }
//        String vcode = (String) obj;
//
//
//        System.out.println("验证码vcode:"+vcode);
//
//        Map map = new HashMap<String, Object>();
//        if (code.equals(vcode)) {
//            map.put("message", "success");
//        } else {
//            map.put("message", "fail");
//        }
//        return map;
//    }

}
