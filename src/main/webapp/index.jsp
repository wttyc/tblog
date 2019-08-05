<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<html>
<head>
    <link href="${ctx}/css/bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<h2>Hello World!</h2>
<br>
<a href="register.jsp">点我注册</a>
<br><br/>
<a href="${ctx}/user/judgeLogin">点我登录</a>
<br><br/><br><br/>
<a href="test.jsp">测试页面</a>
<br><br/>
<a href="/tblog/user/testRedirect">测试重定向</a>

<br><br/>
<button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="top"
        data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
    Popover on 顶aaaaaas
</button>
<br><br/>

<%--&lt;%&ndash;正确代码&ndash;%&gt;--%>
<%--<input id="username" data-trigger="manual" class="popover-show" type="text" data-container="body" data-placement="top"--%>
<%--       value="用户名"/>--%>

<%--<input id="password" data-trigger="manual" class="popover-show" type="text" data-container="body" data-placement="top"--%>
<%--       data-content="test" value="密码"/>--%>


<input id="username" required  type="text" value="用户名"/>

<input id="password" type="text" value="密码"/>


<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/css/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">


    function a() {
        // $('[data-toggle="popover"]').popover({
        $('input').popover({
            html: true,
            placement: 'top',
            trigger: 'manual',

            content: '<div id="content"></div>',
        });
    }


    // function a(){
    //     $('#username').popover('hide');
    // }
    // function b(){
    //     $('#username').popover('show');
    // }

    $(function () {
        $('button').popover();

        a();



        // $('#username').focus(function () {
        //     $(this).popover('hide');
        // });
        // $('#username').blur(function () {
        //     $(this).popover('show');
        // });
        //
        // $('#password').focus(function () {
        //     $(this).popover('hide');
        // });
        // $('#password').blur(function () {
        //     $(this).popover('show');
        // });

        // $('#username').focus(function () {
        //     $("input").css("background-color","#FFFFCC");
        //     $(this).popover('hide');
        // });
        //
        // $('#username').blur(function () {
        //     $("input").css("background-color","#000");
        //     $(this).popover('show');
        // });


    });
    // function checkUser() {
    //     $('#username').blur(function () {
    //         $("input").css("background-color","#FFFFCC");
    //         // $(this).popover('show');
    //     });
    //     $('#username').focus(function () {
    //         $("input").css("background-color","#777221");
    //         // $(this).popover('hide');
    //     });
    // });

</script>
</body>
</html>
