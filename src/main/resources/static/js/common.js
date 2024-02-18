var basePort = "2000"
var baseUrl = "http://39.101.142.240:" + basePort;
/*   线上环境需要切换ip地址  */
function checkNotNull(str) {
    if (str == null || str == "" || str.length < 1 || str == undefined) {
        return false;
    }
    return true;
}
function getCurrentTime() {
    var date = new Date();
    var strDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes();
    return strDate;
}

function getUUID() {
    // 替换所有的“-”为空串即去掉“-”
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    }).replace(/-/g, '');
}

function zuiMsg(msg) {
    new $.zui.Messager(msg, {
        type: 'warning',
        placement: 'center'
    }).show();
}

function zuiSuccessMsg(msg) {
    new $.zui.Messager(msg, {
        type: 'success',
        placement: 'center'
    }).show();
}
function generateOptions(target, optionsData) {
    target.empty(); // 清空已有选项
    optionsData.forEach(function (optionData) {
        target.append(new Option(optionData.articleTypeName, optionData.articleTypeId)); // 添加选项
    });
}
