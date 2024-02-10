package top.lhit.myBlog.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.lhit.myBlog.common.utils.CommonResult;
import top.lhit.myBlog.module.dto.aticle.ArticleTypeUpdateDto;
import top.lhit.myBlog.module.entity.ArticleType;
import top.lhit.myBlog.module.vo.ArticleTypeTreeVo;
import top.lhit.myBlog.module.vo.ArticleTypeVo;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * <p>
 * 文章分类i 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-12-21
 */
public interface IArticleTypeService extends IService<ArticleType> {
    /**
     * 文章类型列表，包含文章数量
     *
     * @return
     */
    List<ArticleTypeVo> articleTypeList();

    CompletionStage<CommonResult> articleTypeAdd(ArticleType articleType);

    CompletionStage<CommonResult> articleTypeDel(String articleTypeId);

    CompletionStage<CommonResult> articleTypeUpdate(ArticleTypeUpdateDto articleTypeUpdateDto);


    /**
     * 获取首页文章类型树形目录
     *
     * @param articleTypeParentId
     * @return
     */
    List<ArticleTypeTreeVo> getIndexArticleTypeList(String articleTypeParentId);

    CompletionStage<CommonResult> getArticleTypeChild(String articleTypeId);
}
