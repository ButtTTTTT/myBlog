package top.lhit.myBlog.module.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.Carousel;
import top.lhit.myBlog.module.service.ICarouselService;

import java.util.List;
import java.util.concurrent.CompletionStage;

@RestController
@Slf4j
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private ICarouselService carouselService;

    @GetMapping("/list")
    public CompletionStage<CommonResult<List<Carousel>>> carouselList() {
        log.info(" /carousel/list -> get :        ");
        return carouselService.listCarousel();
    }

}
