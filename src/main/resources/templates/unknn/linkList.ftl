<#include "../import/unknnTop.ftl">

<#if linkList?? && linkList?size gt 0 >
    <div class="panel">
        <div class="panel-body">
            
            <h4>友联数：${(linkList?size)!0}个</h4>
            
            <hr/>
            <button onclick="addLink()" class="btn btn-mini"><i class="icon-plus"></i> 添加</button>
            <table class="table" style="margin-top: 10px;">
                <thead>
                <tr>
                    <th>友情排序</th>
                    <th>Logo</th>
                    <th>添加时间</th>
                    <th>友情连接标题</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list linkList as link>
                    <tr>
                        <td>${(link.linkSort)!}</td>
                        <td>
                            <img class="thumbnail img-thumbnail" src="${(link.linkLogoUrl)!'/img/null_logo.png'}" alt="友联logo">
                        </td>
                        <td>
                            ${(link.linkAddTime)?string("yyyy-MM-dd HH:mm:ss")}
                        </td>
                        <td>${(link.linkTitle)!}</td>
                        <td>
                            <a href="${(link.linkUrl)!}" target="_blank" class="btn btn-mini"><i
                                        class="icon-eye-open"></i> 查看</a>
                            <button onclick="updateLink('${(link.linkId)!}',
                                    '${(link.linkTitle)!}',
                                    '${(link.linkUrl)!}',
                                    '${(link.linkLogoUrl)!}',
                                    '${(link.linkSort)!}')"
                                    type="button" class="btn btn-mini"><i class="icon-cog"></i> 修改
                            </button>
                            <button onclick="delLink('${(link.linkId)!}')" type="button" class="btn btn-mini"><i
                                        class="icon-remove"></i> 删除
                            </button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
<#else >
    <#include "../import/nullData.ftl">
</#if>


<div class="modal fade" id="linkUpdateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/unknn/link/addOrUpdate" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                                class="sr-only">关闭</span></button>
                    <h4 class="modal-title" id="linkTitleTag">修改友联</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="linkId" id="linkId">
                    <div class="form-group">
                        <label for="linkTitle">友联名称：</label>
                        <input type="text" class="form-control" name="linkTitle" id="linkTitle"
                               placeholder="友联名称">
                    </div>
                    <div class="form-group">
                        <label for="linkLogoUrl">友联Logo：</label>
                        <input type="text" class="form-control" id="linkLogoUrl" name="linkLogoUrl"
                               placeholder="友联排序">
                    </div>
                    <div class="form-group">
                        <label for="linkUrl">友联连接：</label>
                        <input type="text" class="form-control" id="linkUrl" name="linkUrl"
                               placeholder="友联连接">
                    </div>
                    <div class="form-group">
                        <label for="linkSort">友联排序：</label>
                        <input type="number" class="form-control" id="linkSort" name="linkSort"
                               placeholder="友联排序">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="linkAddOrUpdateAction()">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function addLink() {
        $("#linkId").val("");
        $("#linkTitle").val("");
        $("#linkUrl").val("");
        $("#linkLogoUrl").val("");
        $("#linkSort").val("");
        $("#linkTitleTag").text("添加友联");
        $('#linkUpdateModal').modal('toggle', 'center');
    }


    function updateLink(linkId, linkTitle, linkUrl, linkLogoUrl, linkSort) {
        $("#linkTitleTag").text("修改友联");
        $("#linkId").val(linkId);
        $("#linkTitle").val(linkTitle);
        $("#linkUrl").val(linkUrl);
        $("#linkLogoUrl").val(linkLogoUrl);
        $("#linkSort").val(linkSort);
        $('#linkUpdateModal').modal('toggle', 'center');
    }


    function linkAddOrUpdateAction() {
        let linkId = $("#linkId").val();
        let linkTitle = $("#linkTitle").val();
        let linkUrl = $("#linkUrl").val();
        let linkLogoUrl = $("#linkLogoUrl").val();
        let linkSort = $("#linkSort").val();

        if (!checkNotNull(linkTitle) || !checkNotNull(linkUrl) || !checkNotNull(linkSort)) {
            zuiMsg("数据填写不完整");
            return;
        }

        $.post("/unknn/link/addOrUpdate", {
                linkId: linkId,
                linkTitle: linkTitle,
                linkUrl: linkUrl,
                linkLogoUrl: linkLogoUrl,
                linkSort: linkSort
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


    function delLink(linkId) {
        if (confirm("是否删除")) {
            if (!checkNotNull(linkId)) {
                new $.zui.Messager('程序出错，请刷新页面重试', {
                    type: 'warning',
                    placement: 'center'
                }).show();
                return;
            }
            $.post("/unknn/link/del", {
                    linkId: linkId
                },
                function (data) {
                    if (data.code == 200) {
                        alert(data.message)
                        location.reload();
                        return;
                    }
                    new $.zui.Messager(data.message, {
                        type: 'warning',
                        placement: 'center'
                    }).show();
                });
        }
    }


</script>

<#include "../import/bottom.ftl">