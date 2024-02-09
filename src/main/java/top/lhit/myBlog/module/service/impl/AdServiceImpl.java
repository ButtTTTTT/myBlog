package top.lhit.myBlog.module.service.impl;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.ad.AdDto;
import top.lhit.myBlog.module.entity.Ad;
import top.lhit.myBlog.module.mapper.AdMapper;
import top.lhit.myBlog.module.service.IAdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lhit.myBlog.module.service.IUploadFileListService;
import top.lhit.myBlog.module.vo.AdVo;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Objects;
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
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements IAdService {
    @Autowired
    private AdMapper adMapper;
    @Autowired
    private IUploadFileListService uploadFileListService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private IAdService adService;


    /**
     * 广告列表，包含广告类型名称
     *
     * @param adTypeId
     * @return
     */
    @Override
    public List<AdVo> adList(String adTypeId) {
        return adMapper.adList(adTypeId);
    }

    @Override
    public CompletionStage<CommonResult> adDml(AdDto adDto, MultipartFile file) {
        return CompletableFuture.supplyAsync(()->{
            if (Objects.nonNull(file)) {
                //判断是否上传的图片，是否是我们指定的像素
//            BufferedImage read = ImageIO.read(file.getInputStream());
//            if (Objects.isNull(read)) {
//                return CommonResult.failed("请上传图片文件");
//            }
//            int width = read.getWidth();
//            int height = read.getHeight();
//            if (width != 850 || height != 150) {
//                return CommonResult.failed("图片的像素为 850px * 150px");
//            }

                adDto.setAdImgUrl(uploadFileListService.getUploadFileUrl(file));
            }

            String adId = adDto.getAdId();
            Ad ad = new Ad();
            BeanUtils.copyProperties(adDto, ad);
            ad.setAdBeginTime(DateUtil.parseDateTime(adDto.getAdBeginTime()));
            ad.setAdEndTime(DateUtil.parseDateTime(adDto.getAdEndTime()));

            //移除首页广告缓存
            servletContext.removeAttribute("adIndexList");
            servletContext.removeAttribute("adArticleList");

            if (StrUtil.isBlank(adId)) {
                //添加广告类型
                ad.setAdAddTime(DateUtil.date());
                if (adService.save(ad)) {
                    return CommonResult.success("添加成功");
                }
                return CommonResult.success("添加失败");
            }

            //修改广告类型
            if (adService.updateById(ad)) {
                return CommonResult.success("修改成功");
            }
            return CommonResult.failed("修改失败");
        });
    }

    @Override
    public CompletionStage<CommonResult> adDel(String adId) {
        return CompletableFuture.supplyAsync(()->{
            if (adService.removeById(adId)) {
                servletContext.removeAttribute("adIndexList");
                servletContext.removeAttribute("adArticleList");
                return CommonResult.success("删除成功");
            }
            return CommonResult.failed("删除失败");
        });
    }
}
