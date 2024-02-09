<#include "../../import/unknnTop.ftl">
<div class="panel col-xs-12">
    <div class="panel-body">

        <div class="form-inline">
            <div class="form-group">
                <label for="articleTagAdd">标签名称：</label>
                <input class="form-control" type="text" id="articleTagAdd">
            </div>
            <button type="button" onclick="articleTagAdd()" class="btn btn-success">添加</button>
        </div>


        <hr/>
        <#if articleTagList?? && articleTagList?size gt 0 >
            <#list articleTagList as articleTag>
                <div class="col-sm-2" style="padding: 2px;margin-bottom: 5px;">
                    <div class=" img-thumbnail">
                        <div class="pull-right">
                            <i class="icon-cog" data-toggle="tooltip" data-placement="top" title="修改"
                               onclick="articleTagUpdate('${(articleTag.articleTagId)!}')"></i>
                            <i class="icon-remove" data-toggle="tooltip" data-placement="top" title="删除"
                               onclick="articleTagDel('${(articleTag.articleTagId)!}')"></i>
                        </div>
                        <input class="form-control" type="text" id="${(articleTag.articleTagId)!}"
                               value="${(articleTag.articleTagName)!}"/>
                    </div>
                </div>

            </#list>

        <#else >
            <#include "../../import/nullData.ftl">
        </#if>

    </div>
</div>
<script>
    function articleTagAdd() {
        let articleTagName = $("#articleTagAdd").val();
        if (!checkNotNull(articleTagName)) {
            zuiMsg("标签名称不能为空");
            return;
        }

        $.post("/unknn/article/tag/addOrUpdate", {
                articleTagName: articleTagName
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

    function articleTagDel(articleTagId) {
        if (confirm("确定要删除吗？")) {
            $.post("/unknn/article/tag/del", {
                    articleTagId: articleTagId
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

    function articleTagUpdate(articleTagId) {
        if (confirm("确定要修改吗？")) {
            let articleTagName = $("#" + articleTagId).val();
            console.log(articleTagName)
            if (!checkNotNull(articleTagId) || !checkNotNull(articleTagName)) {
                zuiMsg("修改参数不正确");
                return;
            }

            $.post("/unknn/article/tag/addOrUpdate", {
                    articleTagId: articleTagId,
                    articleTagName: articleTagName
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

    $('[data-toggle="tooltip"]').tooltip({
        placement: 'bottom'
    });
</script>
<#include "../../import/bottom.ftl">