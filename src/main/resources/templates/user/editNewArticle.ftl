<#include "../import/top.ftl">
<#include "../import/navbar.ftl">
<style>
    .card img {
        width: 100%;
    }

</style>
<div class="col-xs-12">
    <#include "../import/userManagerNav.ftl">
    <div class="panel col-xs-12 col-sm-9" style="padding: 1px;">
        <div class="panel-heading">
            <i class="icon-edit-sign"></i>编写文章
        </div>
        <div class="panel-body">

            <div class="article_editor_header">
                <#--                头部按钮-->
                <div id="article_post_button  ">
                    <input type="submit" name="pubPost" id="pubPost" value="立即发布" onclick="return checkform();"
                           class="btn btn-success btn-sm">

                    <input type="button" name="savedf" id="savedf" value="保存草稿" onclick="autosave(2);"
                           class="btn btn-success btn-sm" style="background-color: #2a74ea">
                </div>
                <#--  标题  -->
                <div class="article_editor_titile">
                    <label>标题：</label>
                    <input type="text" name="title" id="title" class="form-control" placeholder="标题" value="">
                </div>

                <div>
                    <div class="form-group">
                        <label>封面：</label>
                        <input name="cover" id="cover" class="form-control" placeholder="封面图URL" value="">
                        <br>
                        <img id="coverImg" src="https://demo.emlog.cn/admin/views/images/cover.svg" width="262" height="131">
                        <input type="file" id="coverUpload" style="display: none;"> <!-- 文件上传控件（默认隐藏）-->
                        <button id="coverUploadBtn" class="btn btn-success btn-sm" style="background-color: yellowgreen" >上传本地图片</button>  <!-- 上传按钮-->
                    </div>


                    <div class="form-group">

                        <label>请选择一级文章类型：</label>

                        <select id="primary" class="form-control"></select>

                        <br>

                        <label>请选择二级文章类型：</label>

                        <select id="secondary" class="form-control"></select>

                    </div>

                    <div class="form-group">


                        <label>标签：<small class="text-muted">(也用于页面关键词，英文逗号分隔)</small></label>

                        <input name="tag" id="tag" class="form-control" value="">

                        <div id="tags" style="display: none">

                        </div>

                    </div>

                    <div class="form-group">

                        <label>发布时间：<small
                                    class="text-muted">（当设置未来时间，文章将在该时间点定时发布）</small></label>

                        <input type="text" maxlength="200" name="postdate" id="postdate" value=""
                               class="form-control">

                    </div>

                    <hr>

                </div>

            </div>
            <div class="article_editor_content">

                <label>文章内容:</label>

                <#--                文章内容-->
                <textarea id="contentSimple" name="content" class="form-control kindeditorSimple" style="height:500px;">Hello, world!</textarea>

            </div>
        </div>

    </div>

</div>

<script>

    function  setDefaultTime(){

    }

    <#--
           文章编辑器    内容
      -->
    KindEditor.create('textarea.kindeditorSimple', {
        basePath: '/lib/kindeditor/',
        bodyClass: 'article-content',     // 确保编辑器内的内容也应用 ZUI 排版样式
        cssPath: '/css/zui.css', // 确保编辑器内的内容也应用 ZUI 排版样式
        resizeType: 1,
        allowPreviewEmoticons: false,
        allowImageUpload: false,
        items: [
            'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
            'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
            'insertunorderedlist', '|', 'emoticons', 'image', 'link'
        ]
    });
    $(function () {
        // 页面加载完成后发送Ajax请求获取数据
        $.ajax({
            url: "http://127.0.0.1:2000/acticletype/listAll",
            type: "GET",
            dataType: "json",
            success: function (respData) {
                const primarySelect = $('#primary');
                const secondarySelect = $('#secondary');
                const jsonObj = JSON.parse(respData.data);
                generateOptions(primarySelect, jsonObj); // 生成一级分类选项
                // 监听一级分类选择变化，更新二级分类选项
                primarySelect.change(function () {
                    const selectedPrimaryType = jsonObj.find(
                        type => type.articleTypeId === this.value
                    );
                    generateOptions(secondarySelect, selectedPrimaryType.articleTypeTreeVoList); // 生成二级分类选项
                }).trigger('change'); // 触发一次change事件，生成初始二级分类选项
            },
            error: function (err) {
                console.log("请求失败", err);
            }
        });
    });


    $(document).ready(function() {
        $("#postdate").val(getCurrentTime());
        // 监听输入框的输入事件
        $("#cover").on("input", function() {
            const url = $(this).val(); // 获取输入框的值
            // 检查是否为图片url（简单判断：url以.jpg, .jpeg, .png, .gif, .bmp等结尾）
            if(url.match(/\.(jpeg|jpg|gif|png|bmp|svg)$/i)) {
                // 如果是图片url，显示图片
                $('#coverImg').attr('src', url);
            } else {
                // 否则显示默认图片
                $('#coverImg').attr('src', 'https://demo.emlog.cn/admin/views/images/cover.svg');
            }
        });

        // 点击上传按钮时触发文件上传控件
        $('#coverUploadBtn').click(function() {
            $('#coverUpload').click();
        });

        // 当选择文件后，读取文件并显示图片
        $('#coverUpload').change(function() {
            const file = this.files[0]; // 获取选择的文件
            if(file) {
                const reader = new FileReader(); // 创建FileReader
                reader.onload = function(e) {
                    $('#coverImg').attr('src', e.target.result); // 读取完成后，把图片显示出来
                    $('#cover').val(e.target.result); // 把选择的图片URL填充到输入框中
                };
                reader.readAsDataURL(file); // 读取文件
            }
        });
    });



</script>
<#include "../import/viewBottom.ftl">
