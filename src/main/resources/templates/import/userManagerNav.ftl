<div class="panel col-xs-12 col-sm-3" style="padding: 1px;">
    <div class="panel-body">
        <#--        <nav class="menu" data-ride="menu" style="width: 200px">-->
        <#--            <ul id="treeMenu" class="tree tree-menu" data-ride="tree">-->
        <#--                <li><a href="/user/manager"><i class="icon-paper-clip"></i>我的资料</a></li>-->
        <#--                <li><a href="/user/collection/list?pageNumber=1"><i class="icon-tags"></i>我的收藏</a></li>-->
        <#--                <li><a href="/user/myArticleList?pageNumber=1"><i class="icon-book"></i>我的文章</a></li>-->
        <#--                <li><a href="/acticle/editNewArticle"><i class="icon-edit-sign"></i>编写文章</a></li>-->
        <#--            </ul>-->
        <#--        </nav>-->
        <nav class="menu" data-ride="menu" style="width: 200px">
            <ul id="treeMenu" class="tree tree-menu" data-ride="tree">
                <li><a href="/"><i class="icon icon-th"></i>首页</a></li>
                <li><a href="/user/manager"><i class="icon icon-user"></i>个人资料</a></li>
                <li>
                    <a href="/user/myArticleList?pageNumber=1"><i class="icon-book"></i>我的文章</a>
                    <ul>
                        <li><a href="/acticle/editNewArticle"><i class="icon-edit-sign"></i>编写文章</a></li>
                        <li><a href="/acticle/editNewArticle"><i class="icon-edit-sign"></i>历史文章</a></li>
                        <li><a href="/acticle/editNewArticle"><i class="icon-edit-sign"></i>草稿夹</a></li>
                    </ul>
                </li>
                <li>
                    <a href="/user/myArticleList?pageNumber=1"><i class="icon-book"></i>笔记本</a>
                    <ul>
                        <li><a href="/acticle/editNewArticle"><i class="icon-edit-sign"></i>编写笔记</a></li>
                        <li><a href="/acticle/editNewArticle"><i class="icon-edit-sign"></i>历史笔记</a></li>
                        <li><a href="/acticle/editNewArticle"><i class="icon-edit-sign"></i>笔记草稿</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </div>
</div>
<script>
    // 手动通过点击模拟高亮菜单项
    $('#treeMenu').on('click', 'a', function () {
        $('#treeMenu li.active').removeClass('active');
        $(this).closest('li').addClass('active');
    });
</script>
