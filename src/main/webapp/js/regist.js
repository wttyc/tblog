/**
 * Created by Administrator on 2019/3/19.
 */

//$(function(){


//changeCaptcha = function() {
//    //var ctx = getCtx();
//    alert("时间2kkk1:"+new Date().getTime().toString().substring(11));
//
//    ba();
//
//    //alert(basePath());
//    //alert(ctx);
//    $("#captchaImg").attr('src', basePath+'/captchaServlet?t=' + (new Date().getTime()));
//}
//
//function ba(){
//    alert("ba");
//    var local = window.location;
//    var contextPath = local.pathname.split("/")[1];
//    var basePath = local.protocol+"//"+local.host+"/"+contextPath;
//    alert("base:"+basePath);
//    return basePath;
//}

//function basePath(){
//    alert(new Date().getTime());
//    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
//    var curWwwPath = window.document.location.href;
//    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
//    var pathName = window.document.location.pathname;
//    var pos = curWwwPath.indexOf(pathName);
//    //获取主机地址，如： http://localhost:8080
//    var localhostPath = curWwwPath.substring(0, pos);
//    //获取带"/"的项目名，如：/ems
//    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
//    //获取项目的basePath   http://localhost:8080/ems/
//    var basePath=localhostPath+projectName;
//    return basePath;
//};


//})
//var v = 0;
var phoneHeight = 0;
var emailHeight = 0;
var nickNameHeight = 0;
var passwordHeight = 0;
var captchaHeight = 0;


// var basePath = getBasePath();

// var path = $("#path").val();
//
// console.log("path:"+path);
//
// function path() {
//     var p = $("#path").val();
//     return p;
// }
//
// var as = path();

function path() {
    var path = $("#path").val();
    return path;
}


function changeCaptcha() {
    // $("#captchaImg").attr('src', path() + '/getCaptcha?t=' + (new Date().getTime()));
    $("#captchaImg").attr('src', 'getCaptcha?t=' + (new Date().getTime()));
    //$("#captchaImg").attr('src', basePath + '/captchaServlet?t=' + (new Date().getTime()));
}


// /**
//  * 获取URL
//  * @returns {string}
//  */
// function getBasePath() {
//     var local = window.location;
//     var contextPath = local.pathname.split("/")[1];
//     var basePath = local.protocol + "//" + local.host + "/" + contextPath;
//     console.log("local.protocol:"+local.protocol);
//     console.log("local.host:"+local.host);
//     console.log("contextPath:"+contextPath);
//     // console.log("console:"+context);
//     console.log("basePath: "+basePath);
//     return basePath;
// }

/**
 * 重新注册
 */
function reRegist() {
    window.location.href = "../register.jsp";
}


/**
 * 重新发送邮件
 * @param message
 */
function reSendEmail(message) {
    var arr = message.split(",");
    var email = arr[0];
    var validateCode = arr[1];
    $.ajax({
        type: "post",
        // async:true,
        // cache:false,
        // url: testPath + "/user/sendEmail",

        url: "user/sendEmail",
        // url: "../user/sendEmail",  这个url也可以使用
        data: {email: email, validateCode: validateCode},
        dataType: 'json',


        success: function (data) {
            alert("success:");
            var val = data['message'];
            alert(val);
        },
        error: function (data) {
            alert("error:");
            alert(data["message"]);
        }


        // success: function (data) {
        //     var message = data["message"];
        //     alert("message" + message);
        //     if (message == "success") {
        //         alert("发送成功");
        //     }
        // }
    });

}


/**
 * 根据注册邮箱跳转到不同的邮箱主页
 */
function lookEmail(message) {
    alert("lookEmail");
    var arr = message.split(",");
    var email = arr[0];
    var opt = email.split("@")[1];
    if (opt == "qq.com") {
        window.open("https://mail.qq.com");
    } else if (opt == "163.com") {
        window.open("https://mail.163.com");
    } else if (opt == "126.com") {
        window.open("https://mail.126.com");
    } else if (opt == "sina.com") {
        window.open("https://mail.sina.com");
    } else if (opt == "sohu.com") {
        window.open("https://mail.sohu.com");
    }
}

function register() {
    if (!checkProtocol()) {
        $("#protocol_span").text("请阅读并同意用户协议.").css("color", "red");
    } else {
        $("#protocol_span").text("");
        if (checkNickName() && checkPassword() && checkCode() && checkPhone() && checkEmail()) {
            $("#reg_span").text("");
            $("#registerForm").submit();
        } else {
            $("#reg_span").text("请将信息填写完整.").css("color", "red");
        }
    }

}


/**
 * 校验手机号
 * @returns {boolean}
 */
var phoneFlag = false;

function checkPhone() {
    var phone = $("#phone").val();
    phone.replace(/^\s+|\s+$/g, "");

    if (phone == "" || phone == null) {
        $("#phone_span").text("手机号不能为空.");
        $("#phone_ok").text("×").css("color", "red").css("font-size", "30px");
        //var hgt = $("#regist-left").height();
        if (phoneHeight == 0) {
            increaseHeight();
            phoneHeight++;
        }
        phoneFlag = false;
    } else if (!(/^1[3|4|5|7|8]\d{9}$/.test(phone))) {
        $("#phone_span").text("手机号非法, 请重新输入.");
        $("#phone_ok").text("×").css("color", "red").css("font-size", "30px");
        //var hgt = $("#regist-left").height();
        if (phoneHeight == 0) {
            increaseHeight();
            phoneHeight++;
        }
        phoneFlag = false;

    } else {
        $.ajax({
            type: 'post',
            // url: path() + '/user/checkPhone',
            url: 'user/checkPhone',
            data: {phone: phone},
            dataType: 'json',
            success: function (data) {
                var val = data['message'];

                if (val == "success") {
                    $("#phone_span").text("");
                    $("#phone_ok").text("√").css("color", "green").css("font-size", "20px");
                    if (phoneHeight != 0) {
                        reduceHeight();
                        phoneHeight = 0;
                    }
                    phoneFlag = true;
                } else {
                    $("#phone_span").text("该号码已经注册.");
                    $("#phone_ok").text("×").css("color", "red").css("font-size", "20px");
                    //var hgt = $("#regist-left").height();
                    if (phoneHeight == 0) {
                        increaseHeight();
                        phoneHeight++;
                    }
                    phoneFlag = false;
                }

            }
            // ,
            // error:function(data){
            //     var val = data['message'];
            //     alert("message error : "+message);
            //     console.log("message : "+message);
            //
            // }
        });
    }
    return phoneFlag;
}


/**
 * 校验验证码
 * @type {boolean}
 */
var captchaFlag = false;

function checkCode() {
    var code = $("#code").val();
    code.replace(/^\s+|\s+$/g, "");
    if (code == "") {
        $("#code_span").text("请输入验证码.").css("color", "red").css("font-size", "16px");
        $("#code_ok").text("×").css("color", "red").css("font-size", "30px");
        if (captchaHeight == 0) {
            increaseHeight();
            captchaHeight++;
        }
        captchaFlag = false;
    } else {
        $.ajax({
            type: 'post',
            // url: path() + '/user/checkCode',
            url: 'user/checkCode',
            data: {code: code},
            dataType: 'json',
            success: function (data) {
                var val = data['message'];
                if (val == "success") {
                    $("#code_span").text("");
                    $("#code_ok").text("√").css("color", "green").css("font-size", "20px");
                    if (captchaHeight != 0) {
                        reduceHeight();
                        captchaHeight = 0;
                    }
                    captchaFlag = true;
                } else {
                    $("#code_span").text("验证码错误.").css("color", "red").css("font-size", "16px");
                    $("#code_ok").text("×").css("color", "red").css("font-size", "30px");
                    if (captchaHeight == 0) {
                        increaseHeight();
                        captchaHeight++;
                    }
                    captchaFlag = false;
                }

            }

        });
    }
    return captchaFlag;
}

/**
 * 校验邮箱
 * @type {boolean}
 */
var emailFlag = false;

function checkEmail() {
    var email = $("#email").val();
    email.replace(/^\s+|\s+$/g, "");
    if (email == "") {
        $("#email_ok").text("×").css("color", "red").css("font-size", "30px");
        $("#email_span").text("请输入邮箱.");
        if (emailHeight == 0) {
            increaseHeight();
            emailHeight++;
        }
        emailFlag = false;
    } else if (!(/^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/g.test(email))) {
        $("#email_ok").text("×").css("color", "red").css("font-size", "30px");
        $("#email_span").text("请输入正确的邮箱.");
        if (emailHeight == 0) {
            increaseHeight();
            emailHeight++;
        }
        emailFlag = false;
    } else {
        $.ajax({
            type: 'post',
            // url: path() + '/user/checkEmail',
            url: 'user/checkEmail',
            data: {email: email},
            dataType: 'json',
            success: function (data) {
                var val = data['message'];
                if (val == "success") {
                    $("#email_span").text("");
                    $("#email_ok").text("√").css("color", "green").css("font-size", "20px");
                    if (emailHeight != 0) {
                        reduceHeight();
                        emailHeight = 0;
                    }
                    emailFlag = true;
                } else {
                    $("#email_span").text("该邮箱已经被注册.");
                    $("#email_ok").text("×").css("color", "red").css("font-size", "30px");
                    if (emailHeight == 0) {
                        increaseHeight();
                        emailHeight++;
                    }
                    emailFlag = false;
                }

            }

        });

    }
    return emailFlag;
}

/**
 * 校验密码,6-12位的数字、大小写字母
 * @type {boolean}
 */
var passwordFlag = false;

function checkPassword() {
    //不能为空,长度至少6位
    var password = $("#password").val();
    password.replace(/^\s+|\s+$/g, "");
    if (password == "" || !(/^[\w\u4e00-\u9fa5]{6,16}$/g.test(password))) {
        $("#password_span").text("请输入6至12位的密码 (数字、字母).");
        $("#password_ok").text("×").css("color", "red").css("font-size", "30px");
        if (passwordHeight == 0) {
            increaseHeight();
            passwordHeight++;
        }
        passwordFlag = false;
    } else {
        $("#password_ok").text("√").css("color", "green").css("font-size", "20px");
        $("#password_span").text("");
        if (passwordHeight != 0) {
            reduceHeight();
            passwordHeight = 0;
        }
        passwordFlag = true;
    }
    return passwordFlag;
}

/**
 * 校验昵称
 * @type {boolean}
 */
var nickNameFlag = false;

function checkNickName() {
    var nickName = $("#nickName").val();
    nickName.replace(/^\s+|\s+$/g, "");
    // if(!(/^[\w\u4e00-\u9fa5]{5,16}$/.test(nickName))){
    //     alert("不对");
    // }else{
    //     alert(nickName);
    // }

    var zz = /^[\w\u4e00-\u9fa5~!@#$%^&*(★)_+\-=,./;'{}`"\]:>|<?\\]{5,16}$/;
    if (nickName == "" || !(zz.test(nickName))) {
        // if (nickName=="" || !(/^[\w\u4e00-\u9fa5~!@#$%^&*()_+\-=,./;'{}`"\]:>|<?\\]{5,16}$/.test(nickName))) {
        $("#nickName_ok").text("×").css("color", "red").css("font-size", "30px");
        $("#nickName_span").text("请输入5至16位的昵称(支持汉字、字母、数字).");
        if (nickNameHeight == 0) {
            increaseHeight();
            nickNameHeight++;
        }
        nickNameFlag = false;
    } else {
        $("#nickName_ok").text("√").css("color", "green").css("font-size", "20px");
        $("#nickName_span").text("");
        if (nickNameHeight != 0) {
            reduceHeight();
            nickNameHeight = 0;
        }
        nickNameFlag = true;
    }


    //if (nickName=="" || !(/^{5,16}$/.test(nickName))) {
    //    $("#nickName_ok").text("×").css("color", "red").css("font-size", "30px");
    //    $("#nickName_span").text("请输入5至16位的昵称.");
    //    if (nickNameHeight == 0) {
    //        increaseHeight();
    //        nickNameHeight++;
    //    }
    //    nickNameFlag = false;
    //} else {
    //    $("#nickName_ok").text("√").css("color", "green").css("font-size", "20px");
    //    $("#nickName_span").text("");
    //    if (nickNameHeight != 0) {
    //        reduceHeight();
    //        nickNameHeight = 0;
    //    }
    //    nickNameFlag = true;
    //}
    return nickNameFlag;
}

/**
 * 校验用户协议是否勾选
 * @returns {boolean}
 */
function checkProtocol() {
    // alert("1");
    if ($("#protocol").prop("checked")) {
        $("#reg_span").text("");
        // $("#protocol_span").text("请勾.").css("font-size", "16px");
        // alert("2");
        return true;
    } else {
        // $("#protocol_span").text("请勾选用户协议.").css("color", "red").css("font-size", "16px");
        // alert("3");
        return false;
    }
}


//根据内容增加而增加高度
function increaseHeight() {

    var hgt = $("#regist-left").height();
    $("#regist-left").height(hgt + 30);
    $("#regist-right").height(hgt + 30);

}

//根据内容减少而减少高度
function reduceHeight() {
    var hgt = $("#regist-left").height();
    $("#regist-left").height(hgt - 30);
    $("#regist-right").height(hgt - 30);
}
