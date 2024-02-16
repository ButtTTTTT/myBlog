package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.Carousel;
import top.lhit.myBlog.module.mapper.CarouselMapper;
import top.lhit.myBlog.module.service.ICarouselService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {
    @Autowired
    private ICarouselService carouselService;

    @Override
    public CompletionStage<CommonResult<List<Carousel>>> listCarousel() {

        return CompletableFuture.supplyAsync(() -> {

            List<Carousel> carousels = carouselService

                    .list()

                    .stream()

                    .filter(c -> c.getCarouselIsActive() == 1)

                    .sorted(Comparator.comparing(Carousel::getCarouselSort))

                    .distinct()

                    .collect(Collectors.toList());


            log.info("  ICarouselService.listCarousel:  ->  {} ", JSONUtil.toJsonStr(carousels));

            return CommonResult.success(carousels);

        });
    }

    @Override
    public CompletionStage<CommonResult> delCarousel(String carouselId) {

        return CompletableFuture.supplyAsync(() -> {

            if (carouselService.removeById(carouselId)) {

                log.info("ICarouselService.delCarousel: -> {}", CommonResult.success("删除成功"));

                return CommonResult.success("删除成功");

            }

            log.info("ICarouselService.delCarousel: -> {}", CommonResult.failed("失败"));

            return CommonResult.failed("失败");


        });

    }

    @Override
    public CompletionStage<CommonResult> addOrUpdateCarousel(Carousel carousel) {

        return CompletableFuture.supplyAsync(() -> {

            if (ObjectUtil.isNotNull(carousel)) {

                if (ObjectUtil.isNull(carouselService.getById(carousel.getCarouselId()))) {

                    //拼接#用于target调用id
                    carousel.setCarouselTarget("#" + carousel.getCarouselTarget());

                    if (carouselService.save(carousel)) {

                        log.info("ICarouselService.addOrUpdateCarousel: -> {}", CommonResult.success("添加成功"));

                        return CommonResult.success("添加成功");

                    }

                } else if (carouselService.updateById(carousel)) {

                    log.info("ICarouselService.addOrUpdateCarousel: -> {}", CommonResult.failed("添加成功"));

                    return CommonResult.success("添加成功");

                }

                log.info("ICarouselService.addOrUpdateCarousel: -> {}", CommonResult.failed("添加失败"));

                return CommonResult.failed("添加失败");

            }

            log.info("ICarouselService.addOrUpdateCarousel: -> {}", CommonResult.failed("添加失败"));

            return CommonResult.failed("添加失败");

        });

    }

    @Override
    public CompletionStage<CommonResult> banCarousel(String carouselId) {
        return CompletableFuture.supplyAsync(() -> {

            Carousel carousel = carouselService.getById(carouselId);

            if (ObjectUtil.isNotNull(carousel)) {

                carousel.setCarouselIsActive(0);

                if (carouselService.updateById(carousel)) {

                    log.info("ICarouselService.banCarousel: -> {}", CommonResult.success("禁用成功"));

                    return CommonResult.success("禁用成功");

                }

                log.info("ICarouselService.banCarousel: -> {}", CommonResult.success("失败"));

                return CommonResult.success("失败");

            }

            log.info("ICarouselService.banCarousel: -> {}", CommonResult.success("失败"));

            return CommonResult.success("失败");

        });
    }
}
