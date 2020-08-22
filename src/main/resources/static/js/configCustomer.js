var configResult;
/**
 * 获取配置文件数据
 * @returns  configResult
 */
function getConfigureData() {
    // var configResult = {};
    $.ajax({
        url:"/config/config.json",
        async: false, // 同步
        success: function (data) {
            console.log(data);
            configResult = data;
        },
        error:function (data) {
            console.log("出错了", data);
        }
    })
    return configResult;
}
