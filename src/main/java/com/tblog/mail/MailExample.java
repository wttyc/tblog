package com.tblog.mail;



public class MailExample {


    public static void main (String args[]) throws Exception {
        String email = "406336817@qq.com";
        String validateCode = "34ty4tyhrggfd3434";
        SendEmail.sendEmailMessage(email,validateCode);

    }
}
