let errorCode = localStorage.getItem("errorCode");

if (errorCode == null) {
    $("#error-message").text("无任何异常");
} else {
    let errorMessage = localStorage.getItem("errorMessage");

    let htm = `错误代码：  ${errorCode}\n错误内容:  ${errorMessage}`


    $("#error-message").text(htm);
}

