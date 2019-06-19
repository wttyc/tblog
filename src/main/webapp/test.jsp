<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<c:set var="ctx" value="${pageContext.request.contextPath }"/>--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册页面</title>
    <meta name="keywords" content="梦境网">
    <meta name="content" content="梦境网">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link type="text/css" rel="stylesheet" href="${ctx}/css/dreamland.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

    <script type="text/javascript" src="${ctx}/js/regist.js"></script>

    <script>

        ggg = function(){
            alert("d");
            <%--alert("时间: "+new Date().getTime());--%>
            <%--alert(${ctx});--%>
            <%--alert(${ctx});--%>

        }

        function getCtx(){
            return ${ctx};
        }
    </script>

    <script type="text/javascript" src="${ctx}/js/regist.js"></script>

</head>

<body class="login_bj" style="background-color: grey">

<div class="zhuce_body">
    <div class="zhuce_kong" id="dre_div">
        <div class="zc">
            <div class="bj_bai" style="height: 408px">
                <h3>欢迎注册</h3>
                <form action="${ctx}/doRegister" method="post" id="registerForm">

                    <div>
                        <img id="captchaImg" style="CURSOR: pointer" onclick="changeCaptcha();"
                             title="看不清楚?请点击刷新验证码!" align='absmiddle' src="${ctx}/captchaServlet"
                             height="18" width="55"/>
                        <a href="javascript:;"
                           onClick="changeCaptcha();" style="color: #666;">看不清楚</a> <span id="code_span"
                                                                                          style="color: red"></span>
                    </div>

                    <div>
                        <input id="protocol" type="checkbox" onclick="checkProtocol();"><span>已阅读并同意<a href="#"
                                                                                                       target="_blank"><span
                            class="lan">《梦境网用户协议》</span></a></span>
                        <br/>
                        <span id="protocol_span"></span>
                    </div>

                    <div id="zhuce_btn">
                        <input name="注册" type="button" class="btn_zhuce" id="to_register" value="注册">
                    </div>
                    <br/>
                    <span style="color: red">${error}</span>

                </form>
            </div>
            <div class="bj_right" style="height: 408px">
                <p>使用以下账号直接登录</p>
                <a href="#" class="zhuce_qq">QQ注册</a>
                <a href="#" class="zhuce_wb">微博注册</a>
                <a href="#" class="zhuce_wx">微信注册</a>
                <p>已有账号？<a href="login.html">立即登录</a></p>

            </div>
        </div>
    </div>
</div>

<div style="text-align:center;">
</div>
</body>

<script type="text/javascript">


    var phoneWidth = parseInt(window.screen.width);
    var phoneScale = phoneWidth / 640;
    var ua = navigator.userAgent;
    if (/Android (\d+\.\d+)/.test(ua)) {
        var version = parseFloat(RegExp.$1);
        if (version > 2.3) {
            document.write('<meta name="viewport" content="width=640, minimum-scale = ‘+phoneScale+‘, maximum-scale = ‘+phoneScale+‘, target-densitydpi=device-dpi">');
        } else {
            document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
        }
    } else {
        document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
    }


</script>


</html>