package top.lhit.myBlog.module.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lhit.myBlog.module.entity.ArticleType;
import top.lhit.myBlog.module.vo.ArticleTypeTreeVo;
import top.lhit.myBlog.module.vo.ArticleTypeVo;
import java.util.List;

/**
 * <p>
 * 文章分类i Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-12-21
 */
@Mapper
public interface ArticleTypeMapper extends BaseMapper<ArticleType> {

    /**
     * 文章类型列表VO对象，包含文章数量
     * @return
     */
    List<ArticleTypeVo> articleTypeList();

    /**
     * 获取首页文章类型树形目录
     * @param articleTypeParentId
     * @return
     */
    List<ArticleTypeTreeVo> getIndexArticleTypeList(@Param("articleTypeParentId") String articleTypeParentId);
}
