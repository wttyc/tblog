<%@ page import="com.tblog.common.Captcha" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("pragma", "no-cache");
    response.setHeader("cache-control", "no-cache");
    response.setHeader("expires", "0");

    String code = Captcha.drawImage(request,response);
    session.setAttribute("code", code);


%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <img src>


</body>
</html>
