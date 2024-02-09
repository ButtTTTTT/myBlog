package top.lhit.myBlog.module.service;

import org.springframework.web.multipart.MultipartFile;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.ad.AdDto;
import top.lhit.myBlog.module.entity.Ad;
import com.baomidou.mybatisplus.extension.service.IService;
import top.lhit.myBlog.module.vo.AdVo;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
public interface IAdService extends IService<Ad> {
    /**
     * 广告列表，包含广告类型名称
     * @param adTypeId
     * @return
     */
    List<AdVo> adList(String adTypeId);

    CompletionStage<CommonResult> adDml(AdDto adDto, MultipartFile file);

    CompletionStage<CommonResult> adDel(String adId);
}
