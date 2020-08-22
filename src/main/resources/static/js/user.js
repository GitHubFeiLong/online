// 获取配置文件的 json 字符串
var configResult = getConfigureData();
// token 详细信息,如果本地没有存储token等信息就返回null
let tokenLocal = new Storage().getItem(configResult.token.LOCAL_STORAGE_TOKEN)
// 用户的token
const TOKEN = tokenLocal ? tokenLocal.token : null;

/**
 * 页面加载 就检查token
 * 页面引入该js文件必须在 src="/js/configCustomer.js" src="/js/localStorageCustomer.js" 下面
 * 因为调用另外两个文件的函数
 */
function chenkToken(){
    // 跳转登录页
    if (TOKEN == null) {
        window.location.href = "/login.html"
    } else {
        $.ajax({
            url:"/user/checkToken",
            type:"post",
            async: false,
            headers: {token : TOKEN},
            success:function(data){
                console.log(data);
                if (data.code != "00000") { // 返回不正确，跳转登录页重新登录生成token
                  window.location.href = "/login.html";
                }
            }
        })
    }
}


/**
 * 将错误存储到浏览器中
 * @param data
 */
function setErrorDataToLocalStorage(data) {
    localStorage.setItem("errorCode", data.code)
    localStorage.setItem("errorMessage", data.detailed)
    window.open('/error.html','_blank')
}


/**
 * 自动登录
 * @param tokenResult config.json的 “token” 对应的值
 * @returns {boolean} 自动登录失败返回 false终止执行。成功则根据用户权限跳转对应的页面
 */
function autoLogin(tokenResult){
    const LOCAL_STORAGE_TOKEN = tokenResult.LOCAL_STORAGE_TOKEN;
    let storage = new Storage();
    let obj = storage.getItem(LOCAL_STORAGE_TOKEN)
    if (obj == null) {
        return false;
    }
    let token = obj.token;
    let rememberPassword = obj.rememberPassword;

    // 没有token就不跳转页面
    if (token == null || rememberPassword == false) {
        return false;
    }

    console.log("token是：", token);
    $.ajax({
        url:"/user/autoLogin",
        type:"post",
        async: false,
        headers: {token : token},
        success:function(data){
            console.log(data);
            if (data.code != "00000") { // 返回不正确，跳转登录页重新登录生成token
                return false;
            }
            if (data.isAdmin) { // 管理员，进行选择系统
                window.location.href = "/selectPage.html";
            } else {
                window.location.href = "/index.html";
            }
        }

    })
}

/**
 * 客户执行某些操作需要 request体中带上token
 * @param url 请求的路径
 * @param param 请求的参数
 * @param callbackSuccess 执行成功后的回调函数
 * @param callbackError 执行失败后的回调函数
 */
function customAjaxToken(url, param, callbackSuccess, callbackError){
    var configResult = getConfigureData();
    // token 字符串
    let token = new Storage().getItem(configResult.token.LOCAL_STORAGE_TOKEN).token;
    $.ajax({
        url:url,
        data:param,
        type:"post",
        async: false,
        headers: {token : token},
        success:function(data){
            callbackSuccess(data);
        },
        error:function(data){
            callbackError(data);
        }
    })
}