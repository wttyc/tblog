package com.tblog.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;


public class SendEmail {
    private final static Logger log = Logger.getLogger(SendEmail.class);

    public static void sendEmailMessage(String email, String validateCode)
    //        throws GeneralSecurityException, MessagingException

    {
        try {

            Properties props = new Properties();
            String from = "428370560@qq.com";
            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.qq.com");

            //
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            //开启ssl加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            //
            Session session = Session.getInstance(props);

            Message message = new MimeMessage(session);
            message.setSubject("TBlog账号激活");
//        //设置邮件主体
//        StringBuilder builder = new StringBuilder();
//        builder.append("<a href=\"http://localhost:8080/activecode?email="+email+"&validateCode="+validateCode+"\" target=\"_blank\">请1小时内点击这里进行TBlog账号激活</a>");
//        message.setContent("<a href=\"http://localhost:8080/activecode?email="+email+"&validateCode="+validateCode+"\" target=\"_blank\">请1小时内点击这里进行TBlog账号激活</a>","text/html;charset=gb2312");
//        builder.append("\n如果不能点击,使用以下地址进行激活");
//        builder.append("http://localhost:8080/activecode?email="+email+"&validateCode="+validateCode);
//        message.setText(builder.toString());

            message.setContent("<a href=\"http://localhost:8080/activecode?email=" + email + "&validateCode=" + validateCode + "\" target=\"_blank\">请1小时内点击这里进行TBlog账号激活</a>"
                            + "<br><br>如果不能点击,使用以下地址进行激活"
                            + "<br><br>http://localhost:8080/activecode?email=" + email + "&validateCode=" + validateCode,
                    "text/html;charset=UTF-8");


            message.setFrom(new InternetAddress(from));

            Transport transport = session.getTransport();
            //授权码优先
            transport.connect("smtp.qq.com", from, "mdwudymjbvhhbigb");

            transport.sendMessage(message, new Address[]{new InternetAddress(email)});
            transport.close();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
