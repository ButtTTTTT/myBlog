function checkNotNull(str) {
    if (str == null || str == "" || str.length < 1 || str == undefined) {
        return false;
    }
    return true;
}
function getUUID() {
    // 替换所有的“-”为空串即去掉“-”
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    }).replace(/-/g, '');
}
function zuiMsg(msg){
    new $.zui.Messager(msg, {
        type: 'warning',
        placement: 'center'
    }).show();
}

function zuiSuccessMsg(msg){
    new $.zui.Messager(msg, {
        type: 'success',
        placement: 'center'
    }).show();
}
