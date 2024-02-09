package top.lhit.myBlog.module.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.entity.AdType;
import top.lhit.myBlog.module.mapper.AdTypeMapper;
import top.lhit.myBlog.module.service.IAdTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Service
public class AdTypeServiceImpl extends ServiceImpl<AdTypeMapper, AdType> implements IAdTypeService {
    @Autowired
    private IAdTypeService adTypeService;
    @Override
    public CompletionStage<CommonResult> adTypeUpdate(AdType adType) {
        return CompletableFuture.supplyAsync(()->{
            String adTypeId = adType.getAdTypeId();
            if (StrUtil.isBlank(adTypeId)) {
                //添加广告类型
                adType.setAdTypeAddTime(DateUtil.date());
                if (adTypeService.save(adType)) {
                    return CommonResult.success("添加成功");
                }
                return CommonResult.failed("添加失败");
            }

            //修改广告类型
            if (adTypeService.updateById(adType)) {
                return CommonResult.success("修改成功");
            }
            return CommonResult.failed("修改失败");
        });
    }
}
