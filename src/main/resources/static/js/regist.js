// 邮箱得正则表达式
var emailPattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

// 密码正则 6-16字符
var passwordPattern = /^.{6,16}$/

// 邮箱默认获取鼠标焦点
$('#regist-username').focus();

/**
 * 按钮绑定点击事件
 */
$("#login-body-info").on("click","#login_button", function(){
    console.log("点击登录");
    regist()
});

/**
 * 邮箱失去焦点
 */
$('#regist-username').blur(function(){
    // 发送验证码邮件到邮箱
    sendEmail()
})
/**
 * 邮箱输入框回车事件绑定
 */
$('#regist-username').bind('keypress', function(event) {
    if (event.keyCode == "13") {
        event.preventDefault();
        // 发送验证码邮件到邮箱
        sendEmail()
    }
});

/**
 * 发送邮件
 * @returns {boolean}
 */
function sendEmail(){
    let registUsername = $("#regist-username").val();
    if(registUsername == "" || registUsername == null){
        checkEmail(1)
    } else if(!emailPattern.test(registUsername)){ // 正则判断
        checkEmail(2)
    } else {
        // 异步发送邮件
        $.ajax({
            url:"/user/registSendEmail",
            data:"email="+registUsername,
            type:"post",
            dataType:"json",
            success:function (data) {
                if (data.code != "00000") {
                    // 设置状态码跳转错误页面
                    setErrorDataToLocalStorage(data);
                } else {
                    $("#prompt-head-arrow1").attr("src", "/image/regist/reg-icon_05.png")
                    $("#prompt-head-circle-1").css("background", "#46cd4c");
                    checkEmail(0)
                    let second = 300;
                    let intervalId = setInterval(function(){
                        $("#email-span").text(second);
                        if(second == 0){
                            clearInterval(intervalId);
                            $("#regist-username").val("");
                            checkEmail(1)
                        }else {
                            second--;
                        }
                    },1000);
                }
                console.log(data);
            },
        });
    }
}

/**
 * 密码框获取焦点事件
 */
$("#regist-password").focus(function(){
    let registUsername = $("#regist-username").val();
    // 根据此属性判断 有无发送验证码
    let disableAttr = $("#regist-username").attr("disabled");

    // 没有点击回车就想输入密码
    if (registUsername != null && registUsername != "" && disableAttr == undefined) {
        sendEmail()
    }
})
/**
 * 密码框失去焦点事件
 */
$("#regist-password").blur(function(){
    console.log("密码框失去了焦点")
    let registPassword = $("#regist-password").val();

    // 判断密码
    if (!passwordPattern.test(registPassword)) {
        $("#regist-password-error").css("display", "block");
        $("#regist-password-error").addClass("error");
    } else {
        $("#regist-password-error").css("display", "none");
        $("#prompt-head-circle-4").css("background", "#46cd4c");
        $("#prompt-head-arrow2").attr("src", "/image/regist/reg-icon_05.png")
    }

})

/**
 * 验证码回车事件绑定
 */
$('#regist-pin').bind('keypress', function(event) {
    if (event.keyCode == "13") {
        event.preventDefault();
        //回车执行查询
        regist()
    }
});

/**
 * 输入验证码 进行验证
 * @returns {boolean}
 */
function regist(){
    let registUsername = $("#regist-username").val();
    let registPin = $("#regist-pin").val();
    let registPassword = $("#regist-password").val();
    // 前端只判断是否为空
    if(registUsername == "" || registUsername == null){
        checkEmail(1);
    } else if(registPassword == "" || registPassword == null){
        checkPassword(1)
    } else if(registPin == "" || registPin == null){
        $("#regist-pin-error").html("请输入验证码");
        $("#regist-username-error").removeClass("success");
        $("#regist-pin-error").addClass("error");
        return false;
    } else {
        $.ajax({
            url:"/user/registUser",
            data: {"registUsername":registUsername, "registPassword":registPassword, "registPin":registPin},
            type:"post",
            dataType:"json",
            success:function(data){
                console.log(data);
                console.log(data.code);
                checkPassword(0);
                if(data.code == "00000") {
                    if(data.captchaMatch){
                        $("#prompt-head-circle-3").css("background", "#46cd4c");
                        $("#regist-pin-error").html("验证码输入正确");
                        $("#regist-username-error").removeClass("error");
                        $("#regist-password-error").removeClass("success");
                        $("#regist-pin-error").addClass("success");
                        setTimeout(function(){window.location.href = "/login.html";}, 1000);
                    } else {
                        $("#regist-pin-error").html("验证码输入错误");
                        $("#regist-username-error").removeClass("success");
                        $("#regist-pin-error").addClass("error");
                    }
                } else {
                    setErrorDataToLocalStorage(data);
                }
            }
        })

    }

}

/**
 * 检查Email格式
 * 设置提示内容及样式
 * @param status status:0(正确)；1（未输入）；2（格式或内容错误）
 */
function checkEmail(status){
    switch(status){
        case 0:
            $("#regist-username-error").html("已发送邮件请查收 <span id='email-span'></span> ");
            $("#regist-username-error").removeClass("error");
            $("#regist-username-error").addClass("success");
            $("#regist-username").attr("disabled", "disabled");
            break;
        case 1:
            $("#prompt-head-circle-1").css("background", "#9e9e9e");
            $("#regist-username-error").html("请输入邮箱");
            $("#regist-username-error").removeClass("success");
            $("#regist-username-error").addClass("error");
            $("#regist-username").removeAttr("disabled");
            break;
        case 2:
            $("#prompt-head-circle-1").css("background", "#9e9e9e");
            $("#regist-username-error").html("邮箱格式错误");
            $("#regist-username-error").removeClass("success");
            $("#regist-username-error").addClass("error");
            break;

        default:
            console.log("checkEmail方法参数错误");
    }
}

/**
 * 检查密码
 * @param status status:0(正确)；1（未输入）
 */
function checkPassword(status) {
    switch (status) {
        case 0:
            $("#regist-password-error").html("");
            $("#regist-password-error").removeClass("error");
            $("#regist-password-error").addClass("success");
            break;
        case 1:
            $("#regist-password-error").html("请输入密码");
            $("#regist-password-error").removeClass("success");
            $("#regist-password-error").addClass("error");
            break;
        default:
            console.log("检查密码checkPassword方法参数未知");
    }
}