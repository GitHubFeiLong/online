/**
 *
 * @param content       装table的容器
 * @param url           请求服务器路径
 * @param queryParams   请求服务器的额外参数
 * @param columns       表格列字段信息
 */
function bootstrapTable(content, url, queryParams, columns){
    content.bootstrapTable({
        url: url,//请求路径
        method: 'post',
        contentType:'application/x-www-form-urlencoded; charset=UTF-8',//post请求需设置
        cache: false,   //不缓存
        striped: true, //是否显示行间隔色
        pageNumber: 1, //初始化加载第一页
        pagination:true,//是否分页
        pageSize:10,//单页记录数
        pageList:[5,10,20,30],//可选择单页记录数
        paginationPreText: '上一页',
        paginationNextText: '下一页',
        showRefresh:true,//刷新按钮
        uniqueId:'id',
        undefinedText: "-",//当数据为 undefined 时显示的字符
        clickToSelect : true, //是否启用点击选中行
        singleSelect:true,//开启单选,想要获取被选中的行数据必须要有该参数
        // search: true,
        ajaxOptions:{ // 添加请求头
            headers:{token:TOKEN}
        },
        responseHandler: function(res){ // 后端返回数据 进行判断
            // console.log(res);
            if (res.code == "00000") {
                return {
                    "total" : res.total,
                    "rows" : res.rows,
                };
            } else {
                setErrorDataToLocalStorage(res);
            }

        },
        sidePagination: "server", //服务端处理分页
        queryParamsType : "limit",//设置为 ‘limit’ 则会发送符合 RESTFul 格式的参数.
        queryParams : function (params) {//上传服务器的参数
            var temp = { //如果是在服务器端实现分页，limit、offset这两个参数是必须的
                limit : params.limit, // 每页显示数量
                offset : params.offset, // SQL语句起始索引
                sortName : params.sort,     // 排序字段
                sortBy : params.order,   // 排序方式
            };

            // 添加额外的请求参数
            for (key in queryParams) {
                temp[key] = queryParams[key];
            }

            return temp;
        },
        columns: columns
    })
}








/**
 * 删除元素绑定的exitable
 * @param obj
 */
function editableDestory (obj) {

    // 先替换dom内容
    let value = obj.text()
    obj.parent().html(`<span>${value}</span>`)
    // 再销毁editable 顺序换了会报错
    obj.editable("destroy");
}
/**
 * 删除一组元素绑定的exitable
 * @param arr 数组
 */
function editableAllDestory (arr) {
    for (i = 0; i < arr.length; i++) {
        // 先替换dom内容
        let value = arr[i].text()
        arr[i].parent().html(`<span>${value}</span>`)
        // 再销毁editable 顺序换了会报错
        arr[i].editable("destroy");
    }

}

/**
 * 将当前时间转换成 字符串
 * @returns {string} yyyy-MM-dd HH:mm:ss
 */
function nowDateToString(){
    var now = new Date();
    let year = now.getFullYear();
    let month = now.getMonth() + 1;
    let day = now.getDate();
    let hours = now.getHours();
    let minutes = now.getMinutes();
    let seconds = now.getSeconds()

    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;

}