package top.lhit.myBlog.module.vo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;

/**
 * <p>
 * 文章分类i
 * </p>
 *
 * @author jobob
 * @since 2021-12-21
 */
@Data
public class ArticleTypeVo {


    /**
     * 文章分类id
     */
    private String articleTypeId;

    /**
     * 文章分类父id
     */
    private String articleTypeParentId;

    /**
     * 文章分类名称
     */
    private String articleTypeName;

    /**
     * 文章分类排序，越小越靠前
     */
    private Integer articleTypeSort;

    /**
     * 添加时间
     */
    @TableField(value = "article_type_addTime")
    private Date articleTypeAddTime;
    /**
     * 文章数量
     */
    @TableField(exist = false)
    private Integer articleCount;
}
