<#include "../import/unknnTop.ftl">
<link href="//cdn.bootcdn.net/ajax/libs/smalot-bootstrap-datetimepicker/2.4.4/css/bootstrap-datetimepicker.min.css"
      rel="stylesheet">
<script src="//cdn.bootcdn.net/ajax/libs/smalot-bootstrap-datetimepicker/2.4.4/js/bootstrap-datetimepicker.min.js"></script>
<script src="//cdn.bootcdn.net/ajax/libs/smalot-bootstrap-datetimepicker/2.4.4/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<div class="col-sm-3 img-thumbnail">
    <div class="panel col-sm-12">
        <div class="panel-body">
            <form class="form-horizontal" style="text-align: center">
                <input type="hidden" id="adTypeId" name="adTypeId">
                <div class="form-group">
                    <label for="adTypeTitle" class="col-sm-5">类型名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="adTypeTitle" id="adTypeTitle" placeholder="类型名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="adTypeTag" class="col-sm-5">类型标识</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="adTypeTag" id="adTypeTag" placeholder="类型标识">
                    </div>
                </div>
                <div class="form-group">
                    <label for="adTypeSort" class="col-sm-5">类型排序</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="adTypeSort" id="adTypeSort" placeholder="类型排序">
                    </div>

                </div>
                <div class="form-group " >
                    <label for="adTypeSort" class="col-sm-5"></label>
                    <div class="col-sm-5">
                        <button onclick="subAdTypeUpdate()" type="button" class="btn btn-success">提交</button>
                    </div>

                </div>
            </form>
        </div>
    </div>
    <ul class="list-group col-sm-12">
        <#if adTypeList?? && adTypeList?size gt 0>
            <#list adTypeList as adType>
                <li class="list-group-item">
                    <a href="/unknn/ad/list?adTypeId=${(adType.adTypeId)!}">
                        ${(adType.adTypeTitle)!}
                    </a>
                    <button onclick="adTypeUpdate(
                            '${(adType.adTypeId)!}',
                            '${(adType.adTypeTitle)!}',
                            '${(adType.adTypeTag)!}',
                            '${(adType.adTypeSort)!}'
                            )" type="button" class="btn btn-mini">
                        <i class="icon-cog"></i>
                    </button>
                </li>
            </#list>
        </#if>
    </ul>
</div>

<div class="col-sm-9 img-thumbnail">
    <div class="panel col-sm-12">
        <div class="panel-body">
            <form class="form-horizontal">
                <input type="hidden" id="adId" name="adId">
                <div class="form-group">
                    <label for="adAdTypeId" class="col-sm-2">广告类型：</label>
                    <div class="col-sm-2">
                        <select class="form-control" id="adAdTypeId" name="adAdTypeId">
                            <#if adTypeList?? && adTypeList?size gt 0>
                                <#list adTypeList as adType>
                                    <option value="${(adType.adTypeId)!}">${(adType.adTypeTitle)!}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <label for="adTitle" class="col-sm-2">广告标题：</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="adTitle" id="adTitle" placeholder="广告标题">
                    </div>
                    <label for="adSort" class="col-sm-2">广告排序：</label>
                    <div class="col-sm-2">
                        <input type="number" class="form-control" name="adSort" id="adSort" placeholder="广告排序">
                    </div>
                </div>

                <div class="form-group">
                    <label for="adImgUrlFile" class="col-sm-2">广告图片：</label>
                    <div class="col-sm-4">
                        <input type="file" class="form-control" name="adImgUrlFile" id="adImgUrlFile"
                               placeholder="图片地址">
                    </div>
                    <label for="adLinkUrl" class="col-sm-2">跳转连接：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="adLinkUrl" id="adLinkUrl" placeholder="跳转连接">
                    </div>
                </div>

                <div class="form-group">
                    <label for="adBeginTime" class="col-sm-2">开始时间：</label>
                    <div class="col-sm-4">
                        <input type="text" name="adBeginTime" placeholder="开始时间" id="adBeginTime"
                               class="form-control form-datetime" readonly="readonly">
                    </div>
                    <label for="adEndTime" class="col-sm-2">结束时间：</label>
                    <div class="col-sm-4">
                        <input type="text" name="adEndTime" id="adEndTime" placeholder="结束时间"
                               class="form-control form-datetime" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2"></label>
                    <div class="col-sm-4">
                        <button type="button" onclick="adAddOrUpdate()" class="btn btn-success">提交</button>
                        <button type="button" onclick="adAdd()" class="btn btn-warning">清空</button>
                    </div>
                </div>

            </form>
        </div>
    </div>

    <div class="panel col-sm-12">
        <div class="panel-body">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>广告图片</th>
                    <th>广告类型</th>
                    <th>广告标题</th>
                    <th style="width: 120px;text-align: center;">广告开始时间</th>
                    <th style="width: 120px;text-align: center;">广告结束时间</th>
                    <th style="width: 120px;text-align: center;">添加广告的时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list adVoList as adVo>
                    <tr>
                        <td>
                            <img src="${(adVo.adImgUrl)!'/img/null_logo.png'}" class="thumbnail" alt="广告图片">
                        </td>
                        <td>${(adVo.adTypeTitle)!}</td>
                        <td>${(adVo.adTitle)!}</td>
                        <td style="text-align: center;">${(adVo.adBeginTime)!?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td style="text-align: center;">${(adVo.adEndTime)!?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td style="text-align: center;">${(adVo.adAddTime)!?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>
                            <div>
                                <button onclick="adUpdate(
                                        '${(adVo.adId)!}',
                                        '${(adVo.adTypeId)!}',
                                        '${(adVo.adTitle)!}',
                                        '${(adVo.adImgUrlFile)!}',
                                        '${(adVo.adLinkUrl)!}',
                                        '${(adVo.adSort)!}',
                                        '${(adVo.adBeginTime)!?string("yyyy-MM-dd HH:mm:ss")}',
                                        '${(adVo.adEndTime)!?string("yyyy-MM-dd HH:mm:ss")}',
                                        )" class="btn btn-mini" type="button">修改
                                </button>
                                <button onclick="delAd('${(adVo.adId)!}')" type="button"
                                        class="btn btn-mini">删除
                                </button>
                                <a target="_blank" href="${(adVo.adLinkUrl)!}" class="btn btn-mini">查看</a>
                            </div>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>

        </div>
    </div>
</div>

<script>
    function delAd(adId) {
        if (confirm("是否删除广告")) {
            $.post("/unknn/ad/del", {
                    adId: adId
                },
                function (data) {
                    if (data.code == 200) {
                        alert(data.message)
                        location.reload();
                        return;
                    }
                    zuiMsg(data.message);
                });
        }
    }

    function adAdd() {
        $("#adId").val("");
        $("#adTypeId").val("");
        $("#adTitle").val("");
        $("#adImgUrlFile").val("");
        $("#adLinkUrl").val("");
        $("#adSort").val("");
        $("#adBeginTime").val("");
        $("#adEndTime").val("");

    }

    function adAddOrUpdate() {
        let adId = $("#adId").val();
        let adTypeId = $("#adAdTypeId").val();
        let adTitle = $("#adTitle").val();
        let adImgUrlFile = $("#adImgUrlFile").val();
        let adLinkUrl = $("#adLinkUrl").val();
        let adSort = $("#adSort").val();
        let adBeginTime = $("#adBeginTime").val();
        let adEndTime = $("#adEndTime").val();
        if (!checkNotNull(adId) && !checkNotNull(adImgUrlFile)) {
            zuiMsg("请选择广告图片");
            return;
        }

        if (!checkNotNull(adTypeId) ||
            !checkNotNull(adTitle) ||
            !checkNotNull(adLinkUrl) ||
            !checkNotNull(adSort) ||
            !checkNotNull(adBeginTime) ||
            !checkNotNull(adEndTime)
        ) {
            zuiMsg("参数填写不完整");
            return;
        }


        let formData = new FormData();
        formData.append("file", $("#adImgUrlFile")[0].files[0]);
        formData.append("adId", adId);
        formData.append("adTypeId", adTypeId);
        formData.append("adTitle", adTitle);
        formData.append("adLinkUrl", adLinkUrl);
        formData.append("adSort", adSort);
        formData.append("adBeginTime", adBeginTime);
        formData.append("adEndTime", adEndTime);
        $.ajax({
            url: "/unknn/ad/addOrUpdate",
            type: 'POST',
            data: formData,
            // 告诉jQuery不要去处理发送的数据
            processData: false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType: false,
            beforeSend: function () {
                console.log("提交中，请稍候");
            },
            success: function (data) {
                if (data.code == 200) {
                    alert(data.message)
                    location.reload();
                    return;
                }
                zuiMsg(data.message);
            },
            error: function (responseStr) {
                console.log("error");
            }
        });

    }

    function subAdTypeUpdate() {
        let adTypeId = $("#adTypeId").val();
        let adTypeTitle = $("#adTypeTitle").val();
        let adTypeTag = $("#adTypeTag").val();
        let adTypeSort = $("#adTypeSort").val();
        if (!checkNotNull(adTypeTitle) || !checkNotNull(adTypeTag) || !checkNotNull(adTypeSort)) {
            zuiMsg("参数不完整");
            return;
        }

        $.post("/unknn/ad/type/addOrUpdate", {
                adTypeId: adTypeId,
                adTypeTitle: adTypeTitle,
                adTypeTag: adTypeTag,
                adTypeSort: adTypeSort
            },
            function (data) {
                if (data.code == 200) {
                    alert(data.message)
                    location.reload();
                    return;
                }
                zuiMsg(data.message);
            });
    }

    function adTypeUpdate(adTypeId, adTypeTitle, adTypeTag, adTypeSort) {
        $("#adTypeId").val(adTypeId);
        $("#adTypeTitle").val(adTypeTitle);
        $("#adTypeTag").val(adTypeTag);
        $("#adTypeSort").val(adTypeSort);
    }


    function adUpdate(adId, adTypeId, adTitle, adImgUrlFile, adLinkUrl, adSort, adBeginTime, adEndTime) {
        $("#adId").val(adId);
        $('#adAdTypeId option[value="' + adTypeId + '"]').prop("selected", "selected");
        $("#adTitle").val(adTitle);
        $("#adImgUrlFile").val(adImgUrlFile);
        $("#adLinkUrl").val(adLinkUrl);
        $("#adSort").val(adSort);
        $("#adBeginTime").val(adBeginTime);
        $("#adEndTime").val(adEndTime);
    }


    $(".form-datetime").datetimepicker(
        {
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            format: "yyyy-mm-dd hh:ii:ss"
        });
</script>
<#include "../import/bottom.ftl">