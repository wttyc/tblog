package com.tblog.controller;

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
@RequestMapping("/user")
public class UserController {

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

    /**
     * 重新发送激活邮件
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendEmail")
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
        System.out.println("nickName" + nickName);
        System.out.println("email" + email);
        System.out.println("password" + password);
        System.out.println("phone" + phone);
        System.out.println("code" + code);
        logger.debug("注册...");
        if (StringUtils.isBlank(code)) {
            model.addAttribute("error", "非法注册,请重新注册!");
            return "../register";
        }
        int b = checkValidateCode(code);
        if (b == -1) {
            model.addAttribute("error", "验证码超时,请重新注册!");
            return "../register";
        } else if (b == 0) {
            model.addAttribute("error", "验证码错误,请重新输入!");
            return "../register";
        }

        User user = userService.findByEmail(email);
        /**
         *  TODO: 2019/6/22
         *   不验证 email 是否重复
         *   验证则需要将以下两行互换
         *   即使用 if (user != null) {
         */
        //if (user != null) {
        if (false) {


            model.addAttribute("error", "该邮箱已经注册,请登录或更换邮箱后注册!");
            return "../register";
        } else {
            user = new User();


            user.setEmail(email);
            user.setNickName(nickName);
            user.setPassword(MD5Utils.encodeToHex(password + "tsalt"));
            user.setEnable("0");
            user.setState("0");
            user.setPhone(phone);
            user.setImgUrl("/images/icon_m.jpg");

            //邮件激活码F
            String validateCode = MD5Utils.encodeToHex(email + System.currentTimeMillis());
            //1小时内激活有效
            redisTemplate.opsForValue().set(email, validateCode, 1, TimeUnit.HOURS);

            userService.regist(user);

            logger.info("注册成功.");

            SendEmail.sendEmailMessage(email, validateCode);

            String message = email + "," + validateCode;
            model.addAttribute("message", message);
            return "/regist/registerSuccess";

        }

    }

    /**
     * 校验激活码
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
            System.out.println("user is null");
            map.put("message", "success");
        } else {
            System.out.println("user is not null");
            map.put("message", "fail");
        }
        return map;
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkEmail(Model model, @RequestParam(value = "email", required = false) String email) {
        logger.info("注册---判断邮箱" + email + "是否可用");
        Map map = new HashMap<String, Object>();
        User user = userService.findByEmail(email);
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

        logger.info("注册---判断验证码" + code + "是否正确");
        Map map = new HashMap<String, Object>();
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String captcha = (String) attrs.getRequest().getSession().getAttribute("captcha");


        if (code.equalsIgnoreCase(captcha)) {
            map.put("message", "success");
        } else {
            map.put("message", "fail");
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
