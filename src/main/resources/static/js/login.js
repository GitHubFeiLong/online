// 获取配置文件数据
var configResult = getConfigureData();
var tokenResult = configResult.token;

// token自动登录
autoLogin(tokenResult);

const EWM_EXPIRED = configResult.EWM_EXPIRED;//二维码过期时间
var ewmIsExpired = false;//默认未过期
var wemTimeOutId;
// 二维码移入移出事件
ewmHover()


/**
 * 二维码移入移出事件
 */
function ewmHover() {
    $(".ewm").hover(
        function () {
            // console.log("移入");
            $(".ewm-right").css("display", "block");
        },
        function () {
            // console.log("移出");
            $(".ewm-right").css("display", "none");
        }
    )
}

/**
 * 扫码方式登录
 */
function switchLoginQRcode(obj) {
    // 修改a标签样式
    $(".login-a").each(function (i, x) {
        $(x).removeClass("login-a");
    });
    $(obj).addClass("login-a");
    $("#loggin-div").html("");
    let txt = `<div class="ewm">
                <div id="ewm-left" class="ewm-left">
                <img src="/image/login/show.png" alt="二维码" />
                </div>
                <div class="ewm-right">
                <img src="/image/login/phone-orange.png" alt="二维码" style="width:100%;height:100%"/>
                </div>
                </div>
                <div id="ewm-mask">
                <div style="color: white;font-weight: 700;">
                <span>二维码已失效</span>
                </div>
                <div onclick="flushEwm()" style="width: 80px;height: 30px; background: #e4393c;line-height: 30px; opacity: 1;z-index: 19;color: #fbfbfb;margin-top: 8px;cursor:pointer;">
                <span>刷新</span>
                </div>
                </div>
                <div class="ewm-bottom">
                打开<span>手机狗东</span> &nbsp;扫描二维码
                </div>
                <div class="ewm-features">
                <div>
                <b class="icon-1"></b>
                &nbsp;
        <span>免输入</span>
            </div>
            <div>
            <b class="icon-2"></b>
                &nbsp;
        <span>更快</span>
            </div>
            <div>
            <b class="icon-3"></b>
                &nbsp;
        <span>更安全</span>
            </div>
            </div>`

    $("#loggin-div").html(txt);

    // 修改框大小
    $("#login-body-info").height("428px");
    $("#loggin-div").height("280px");

    if (ewmIsExpired) {
        $("#ewm-mask").css("display", "flex");
    } else {
        flushEwm();
    }

    ewmHover();

}

/**
 * 手动输入登录方式
 */
function switchLoginEntry(obj) {
    //修改a标签样式
    $(".login-a").each(function (i, x) {
        $(x).removeClass("login-a");
    });
    $(obj).addClass("login-a");

    $("#loggin-div").html("");
    let html = "<div class=\"entry-login\">\n" +
        "                                        <label id='login_username_label' class=\"bottom-icon\" for=\"login_username\" style=\"background: url('/image/login/pwd-icons-new_01.png')\"></label>\n" +
        "                                        <input id=\"login_username\" type=\"text\" placeholder=\"邮箱/用户名/登录手机\"/>\n" +
        "                                    </div>\n" +
        "                                    <div class=\"entry-login\">\n" +
        "                                        <label id='login_password_label' class=\"bottom-icon\" for=\"login_password\" style=\"background: url('/image/login/pwd-icons-new_03.png')\"></label>\n" +
        "                                        <input id=\"login_password\" type=\"password\" placeholder=\"密码\" autocomplete=\"off\"/>\n" +
        "                                    </div>\n" +
        "                                    <div class=\"enter-forget-password\">\n" +
        "                                       <div><label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"remember_password\" value=\"option1\">自动登录</label> </div>\n" +
        "                                    </div>\n" +
        "                                    <div class=\"enter-button\">\n" +
        "                                        <input id='login_button' type=\"button\" value=\"登   录\" onclick='login()'/>\n" +
        "                                    </div>"
    $("#loggin-div").html(html);

    // 修改框大小
    $("#login-body-info").height("398px");
    $("#loggin-div").height("250px");

}


// 二维码过期函数
flushEwm();

/**
 * 二维码蒙版
 * 点击按钮刷新或页面加载时执行
 */
function flushEwm() {
    // 隐藏二维码蒙版
    $("#ewm-mask").css("display", "none");

    wemTimeOutId = setTimeout(function () {
        // 启动二维码蒙版
        $("#ewm-mask").css("display", "flex");
        ewmIsExpired = true;
        clearTimeout(wemTimeOutId);
    }, EWM_EXPIRED);
}

/**
 * 跳转注册页面
 */
$("#regist_span").click(function () {
    window.location.href = "/regist.html"
})

/**
 * 点击登录
 */
function login() {
    let loginUsername = $("#login_username").val();
    let loginPassword = $("#login_password").val();
    let rememberPassword = $("#remember_password").is(":checked");


    let boo1 = checkUsernameIsNull(loginUsername);
    let boo2 = checkPasswordIsNull(loginPassword);

    if (boo1 && boo2) {
        $.ajax({
            url: "/user/login",
            data: {"loginUsername": loginUsername, "loginPassword": loginPassword, "rememberPassword" : rememberPassword},
            type: "post",
            dataType: "json",
            success: function (data) {
                console.log(data);
                switch (data.code) {
                    case "00000":
                        console.log("登录成功");
                        // 设置token到localStorage 指定时间删除
                        let storage = new Storage();
                        storage.setItem({
                            name: tokenResult.LOCAL_STORAGE_TOKEN,
                            value: {"token":data.token, "rememberPassword":rememberPassword},
                            expires: tokenResult.LOCAL_STORAGE_TOKEN_EXPIRES
                        });
                        if (data.isAdmin) { // 管理员，进行选择系统
                            window.location.href = "/selectPage.html";
                        } else {
                            window.location.href = "/index.html";
                        }

                        break;
                    case "A0108":
                        // 用户名错误
                        $("#login_username_label").css("background", "url('/image/login/pwd-icons-new_02.png')")
                        alert(data.message);
                        break;
                    case "A0111":
                        // 密码错误
                        $("#login_password_label").css("background", "url('/image/login/pwd-icons-new_04.png')")
                        alert(data.message);
                        break;
                    default:
                        // 默认出错了，显示错误信息
                        setErrorDataToLocalStorage(data);
                }

            }
        })
    }
}

/**
 * 检查用户名，修改label的背景图片
 * @param loginUsername
 * @returns {boolean}
 */
function checkUsernameIsNull(loginUsername) {
    if (loginUsername == "" || loginUsername == null) {
        $("#login_username_label").css("background", "url('/image/login/pwd-icons-new_02.png')")
        return false;
    } else {
        $("#login_username_label").css("background", "url('/image/login/pwd-icons-new_01.png')")
        return true;
    }
}

/**
 * 检查密码，修改label的背景图片
 * @param loginPassword
 * @returns {boolean}
 */
function checkPasswordIsNull(loginPassword) {
    if (loginPassword == "" || loginPassword == null) {
        $("#login_password_label").css("background", "url('/image/login/pwd-icons-new_04.png')")
        return false;
    } else {
        $("#login_password_label").css("background", "url('/image/login/pwd-icons-new_03.png')")
        return true;
    }
}

/**
 * 密码框绑定回车事件
 */
$("#login-body-info").bind("keypress", "#login_password", function () {
    if (event.keyCode == "13") {
        // event.preventDefault();
        login()
    }
});

$(".login-header-center-left-img").click(function(){
    window.location.href="/index.html"
})