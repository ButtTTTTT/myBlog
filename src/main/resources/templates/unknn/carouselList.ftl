<#include "../import/unknnTop.ftl">
<#if carouselList?? && carouselList ?size gt 0 >
    <div class="panel">
        <div class="panel-body">
            <h4>轮播图数：${(carouselList?size)!0}个</h4>
            <hr/>
            <button onclick="addCarousel()" class="btn btn-mini"><i class="icon-plus"></i> 添加</button>
            <table class="table" style="margin-top: 10px;">
                <thead>
                <tr>
                    <th>轮播图排序</th>
                    <th>图片</th>
                    <th>轮播图标题</th>
                    <th>轮播图内容</th>
                    <th>轮播图位置</th>
                    <th>是否可用</th>
                    <th>添加时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list carouselList as carousel>
                    <tr>
                        <#--       轮播图排序-->
                        <td>${(carousel.carouselSort)!}</td>

                        <#--                        图片-->
                        <td>
                            <img class="thumbnail img-thumbnail"
                                 src="${(carousel.carouselImageUrl)!'/img/null_logo.png'}" alt="轮播图img">
                        </td>


                        <#--                        轮播图标题-->
                        <td>${(carousel.carouselTitle)!}</td>
<#--                        轮播图内容-->
                        <td>${(carousel.carouselContent)!}</td>
                        <#--                        轮播图位置-->
                        <td>${(carousel.carouselTarget)!}</td>

                        <#--                  判断     轮播图 是否可用-->
                        <td>
                            <#if (carousel.carouselIsActive)?? && (carousel.carouselIsActive)==0>
                                <span class="label label-danger">禁用</span>
                            <#else >
                                <span class="label label-success">正常</span>
                            </#if>
                        </td>

                        <#--                        添加时间-->
                        <td>
                            ${(carousel.carouselCreateTime)?string("yyyy-MM-dd HH:mm:ss")}
                        </td>

                        <#--                        操作面板-->
                        <td>

                            <button onclick="updateCarousel(
                                    '${(carousel.carouselId)!}',
                                    '${(carousel.carouselSort)!}',
                                    '${(carousel.carouselImageUrl)!}',
                                    '${(carousel.carouselTitle)!}',
                                    '${(carousel.carouselContent)!}',
                                    '${(carousel.carouselIsActive)!}',
                                    '${(carousel.carouselTarget)!}')"
                                    type="button" class="btn btn-mini"><i class="icon-cog"></i> 修改
                            </button>


                            <button onclick="banCarousel('${(carousel.carouselId)!}')" type="button"
                                    class="btn btn-mini"><i
                                        class="icon icon-ban-circle"></i> 禁用
                            </button>


                            <button onclick="delCarousel('${(carousel.carouselId)!}')" type="button"
                                    class="btn btn-mini"><i
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


<div class="modal fade" id="carouselUpdateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/unknn/carousel/addOrUpdate" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                                class="sr-only">关闭</span></button>
                    <h4 class="modal-title" id="carouselTitleTag">修改轮播图</h4>
                </div>
                <div class="modal-body">

                    <input type="hidden" name="carouselId" id="carouselId">

                    <div class="form-group">
                        <label for="carouselTitle">轮播图标题：</label>
                        <input type="text" class="form-control" name="carouselTitle" id="carouselTitle"
                               placeholder="轮播图标题">
                    </div>

                    <div class="form-group">
                        <label for="carouselContent">轮播图内容：</label>
                        <input type="text" class="form-control" id="carouselContent" name="carouselContent"
                               placeholder="轮播图内容">
                    </div>

                    <div class="form-group">
                        <label for="carouselImageUrl">轮播图图片地址：</label>
                        <input type="text" class="form-control" id="carouselImageUrl" name="carouselImageUrl"
                               placeholder="轮播图图片地址">
                    </div>

                    <div class="form-group">
                        <label for="carouselIsActive">轮播图状态 1为正常 0 为停用</label>
                        <div class="input-group">
                            <label class="radio-inline">
                                <input type="radio" name="carouselIsActive" value="1" checked="checked"> 正常
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="carouselIsActive" value="0"> 停用
                            </label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="carouselSort">轮播图排序：</label>
                        <input type="number" class="form-control" id="carouselSort" name="carouselSort"
                               placeholder="轮播图排序">
                    </div>

                    <div class="form-group">
                        <label for="carouselTarget">轮播图位置：</label>
                        <input type="text" class="form-control" id="carouselTarget" name="carouselTarget"
                               placeholder="轮播图位置">
                    </div>

                </div>

                <div class="modal-footer">

                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

                    <button type="button" class="btn btn-primary" onclick="carouselAddOrUpdateAction()">保存</button>

                </div>

            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function addCarousel() {

        $("#carouselId").val(getUUID());

        $("#carouselSort").val("");

        $("#carouselImageUrl").val("");

        $("#carouselTitle").val("");

        $("#carouselContent").val("");

        $("#carouselTarget").val("");

        $("#carouselTitleTag").text("添加轮播图");
        $('#carouselUpdateModal').modal('toggle', 'center');
    }

    function updateCarousel(carouselId, carouselSort, carouselImageUrl, carouselTitle, carouselContent, carouselIsActive, carouselTarget) {
        $("#carouselTitleTag").text("修改轮播图");

        $("#carouselId").val(carouselId);

        $("#carouselSort").val(carouselSort);

        $("#carouselImageUrl").val(carouselImageUrl);

        $("#carouselTitle").val(carouselTitle);

        $("#carouselContent").val(carouselContent);

        $(":radio[name='carouselIsActive'][value='" + carouselIsActive + "']").prop("checked", "checked");

        $("#carouselTarget").val(carouselTarget);

        $('#carouselUpdateModal').modal('toggle', 'center');
    }


    function carouselAddOrUpdateAction() {

        let carouselId = $("#carouselId").val();

        let carouselSort = $("#carouselSort").val();

        let carouselImageUrl = $("#carouselImageUrl").val();

        let carouselTitle = $("#carouselTitle").val();

        let carouselContent = $("#carouselContent").val();

        let carouselIsActive = $("input[name='carouselIsActive']:checked").val();

        let carouselTarget = $("#carouselTarget").val();

        let carouselCreateTime = getCurrentTime()

        if (!checkNotNull(carouselId) ||
            !checkNotNull(carouselSort) ||
            !checkNotNull(carouselImageUrl) ||
            !checkNotNull(carouselTitle) ||
            !checkNotNull(carouselContent) ||
            !checkNotNull(carouselIsActive) ||
            !checkNotNull(carouselTarget) ||
            !checkNotNull(carouselCreateTime)
        ) {
            zuiMsg("数据填写不完整");
            return;
        }
        $.post("/unknn/carousel/addOrUpdate", {
                carouselId: carouselId,
                carouselSort: carouselSort,
                carouselImageUrl: carouselImageUrl,
                carouselTitle: carouselTitle,
                carouselContent: carouselContent,
                carouselIsActive: carouselIsActive,
                carouselTarget: carouselTarget,
                carouselCreateTime: carouselCreateTime
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

    function delCarousel(carouselId) {
        if (confirm("是否删除")) {
            if (!checkNotNull(carouselId)) {
                new $.zui.Messager('程序出错，请刷新页面重试', {
                    type: 'warning',
                    placement: 'center'
                }).show();
                return;
            }
            $.post("/unknn/carousel/del", {
                    carouselId: carouselId
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

    function banCarousel(carouselId) {

        if (confirm("是否禁用")) {

            if (!checkNotNull(carouselId)) {

                new $.zui.Messager('程序出错，请刷新页面重试', {

                    type: 'warning',

                    placement: 'center'

                }).show();

                return;
            }

            $.post("/unknn/carousel/ban", {

                    carouselId: carouselId

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