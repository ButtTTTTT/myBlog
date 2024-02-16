<div class="col-xs-12 col2Padding" style="margin-left: 10px; margin-bottom: 20px;">
    <div class="panel col-xs-12 col-sm-4">
        <div class="panel-body">
            <b>分享生活-感受生活</b>
        </div>
    </div>
    <div class="panel col-xs-12 col-sm-8">
        <div class="panel-body">
            <b>友情连接：</b>
            <#if linkList?? && linkList?size gt 0 >
                <#list linkList as link>
                    <a target="_blank" href="${(link.linkUrl)!}">${(link.linkTitle)!}</a> |
                </#list>
            </#if>
        </div>
    </div>
</div>
</div>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?bb2411e03774d0c80a4a78a5ab02443a";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
</body>
</html>