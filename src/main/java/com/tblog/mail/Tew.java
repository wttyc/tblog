//package com.tblog.mail;
//
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
///**
// * @author tyc
// * @date 2019/6/10
// */
//public class Tew {
//    try {
//           //发邮件的目的地（收件人信箱）
//           String to = email;
//
//           MyAuthenticator myauth = new MyAuthenticator(from, "你的授权码");
//           Session session = Session.getDefaultInstance(props, myauth);
//
////    session.setDebug(true);
//
//           // Define message
//           MimeMessage message = new MimeMessage(session);
//
//           // Set the from address
//           message.setFrom(new InternetAddress(from));
//
//           // Set the to address
//           message.addRecipient( Message.RecipientType.TO,
//                   new InternetAddress(to));
//
//
//           // Set the content
//           message.setContent( "<a href=\"http://localhost:8080/activecode?email="+email+"&validateCode="+validateCode+"\" target=\"_blank\">请于24小时内点击激活</a>","text/html;charset=gb2312");
//           message.saveChanges();
//
//           Transport.send(message);
//
//           log.info( "send validateCode to " + email );
//       }catch (Exception e){
//
//           log.info( "Send Email Exception:"+e.getMessage() );
//       }
//}
