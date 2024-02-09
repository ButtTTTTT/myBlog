package top.lhit.myBlog.module.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ArticleType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文章分类id
     */
    @TableId(value = "article_type_id")
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
    private Date articleTypeAddTime;
}
