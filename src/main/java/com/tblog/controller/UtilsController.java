package com.tblog.controller;

import com.tblog.common.Captcha;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tyc
 * @date 2019/5/10
 */
@RestController
public class UtilsController {

    @RequestMapping(value = "/**/getCaptcha",method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        Captcha.drawImage(request,response);
    }
}
