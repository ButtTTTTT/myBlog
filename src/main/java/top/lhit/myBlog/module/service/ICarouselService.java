package top.lhit.myBlog.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.Carousel;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface ICarouselService  extends IService<Carousel> {
    CompletionStage<CommonResult<List<Carousel>>> listCarousel();

    CompletionStage<CommonResult> delCarousel(String carouselId);

    CompletionStage<CommonResult> addOrUpdateCarousel(Carousel carousel);

    CompletionStage<CommonResult> banCarousel(String carouselId);
}
