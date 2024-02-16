<div class="pl">
<#--    轮播图 开始  -->
<div id="indexCarousel" class="carousel slide" data-ride="carousel">
    <!-- 圆点指示器 -->
    <ol class="carousel-indicators" id="carousel-indicators">
    </ol>
    <!-- 轮播项目 -->
    <div class="carousel-inner" id="carousel-inner">
    </div>
    <!-- 项目切换按钮 -->
    <a class="left carousel-control" href="#indexCarousel" data-slide="prev">
        <span class="icon icon-chevron-left"></span>
    </a>

    <a class="right carousel-control" href="#indexCarousel" data-slide="next">
        <span class="icon icon-chevron-right"></span>
    </a>
</div>
</div>
<#--    轮播图 结束  -->
<script>
    var res = [];

    function getCarousel() {
        return $.ajax({
            url: baseUrl + '/carousel/list',
            type: 'GET',
            contentType: "application/json"
        });
    }

    function initCarousel(data) {
        var carouselItem = '';
        var carouselIndicators = '';
        for (var i = 0; i < data.length; i++) {
            //carousel-indicators 开始
            carouselIndicators += '<li data-target="'+data[i].carouselTarget+'"data-slide-to="'+i+'" class="' + (i === 0 ? 'active' : '') + '"></li>';

            //carouselItem 开始
            carouselItem += '<div class="item' + (i === 0 ? ' active' : '') + '">';

            carouselItem += '<img  src="' + data[i].carouselImageUrl + '" alt="' + data[i].carouselTitle + '">';

            carouselItem += '<div class="carousel-caption">';

            carouselItem += '<h3>' + data[i].carouselTitle + '</h3>';

            carouselItem += '<p>' + data[i].carouselContent + '</p>';

            carouselItem += '</div>';

            carouselItem += '</div>';

        }
        $('#carousel-indicators').html(carouselIndicators);
        $('#carousel-inner').html(carouselItem);
    }

    getCarousel().done(function initRes(data) {
        res = data.data;
        initCarousel(res)
    });

</script>