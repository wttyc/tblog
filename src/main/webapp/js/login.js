/**
 * 更改验证码
 */
function changeCaptcha() {
    $("#captchaImg").attr('src', 'getCaptcha?t=' + (new Date().getTime()));
}

/**
 * 账号密码登录
 */
function normal_login() {
    if (checkCode() && checkUserName() && checkPassword()) {
        $("#normal_form").submit();
    }
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
                    $("#code_span").text("✔").css("color", "green").css("font-size", "22px");

                    captchaFlag = true;
                } else {
                    $("#code_span").text("验证码错误.").css("color", "red").css("font-size", "16px");

                    captchaFlag = false;
                }

            }

        });
    }
    return captchaFlag;
}

/**
 * 校验用户名(邮箱)
 * @type {boolean}
 */
var usernameFlag = false;

function checkUserName() {
    var username = $("#username").val();
    username.replace(/^\s+|\s+$/g, "");
    if (username == "") {
        $("#normal_span").css("color", "red");
        $("#normal_span").text("请输入用户名.");
        usernameFlag = false;
    } else if (!(/^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/g.test(username))) {
        $("#normal_span").css("color", "red");
        $("#normal_span").text("请输入正确的用户名.");
        usernameFlag = false;
    } else {
        /**
         * TODO  暂不用ajax 验证用户名是否存在和可用
         */

        // $.ajax({
        //     type: 'post',
        //     // url: path() + '/user/checkEmail',
        //     url: 'user/checkEmail',
        //     data: {email: email},
        //     dataType: 'json',
        //     success: function (data) {
        //         var val = data['message'];
        //         if (val == "success") {
        //             $("#email_span").text("");
        //             $("#email_ok").text("√").css("color", "green").css("font-size", "20px");
        //
        //             emailFlag = true;
        //         } else {
        //             $("#email_span").text("该邮箱已经被注册.");
        //             $("#email_ok").text("×").css("color", "red").css("font-size", "30px");
        //
        //             emailFlag = false;
        //         }
        //
        //     }
        //
        // });

        $("#normal_span").text("");
        usernameFlag = true;
    }
    return usernameFlag;
}

/**
 * 校验密码,6-12位的数字、大小写字母
 * @type {boolean}
 */
var passwordFlag = false;

function checkPassword() {

    var password = $("#password").val();
    password.replace(/^\s+|\s+$/g, "");
    if (password == "") {

        $("#normal_span").css("color", "red");
        $("#normal_span").text("请输入密码.");
        passwordFlag = false;
    } else if (!(/^[\w\u4e00-\u9fa5]{6,16}$/g.test(password))) {
        $("#normal_span").css("color", "red");
        $("#normal_span").text("密码长度错误,请输入6-16位的密码.");
        passwordFlag = false;
    } else {
        $("#normal_span").text("");
        passwordFlag = true;
    }
    return passwordFlag;
}