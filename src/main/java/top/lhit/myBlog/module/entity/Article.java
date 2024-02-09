package top.lhit.myBlog.module.entity;
import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 文章id
     */
    @TableId(value = "article_id")
    private String articleId;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 文章添加时间
     */
    private Date articleAddTime;
    /**
     * 文章内容
     */
    private String articleContext;
    /**
     * 文章封面url
     */
    private String articleCoverUrl;
    /**
     * 是否是热门文章 0否，1是
     */
    private Integer articleHot;
    /**
     * 观看次数
     */
    private Integer articleWatchTimes;

    /**
     * 点赞次数
     */
    private Integer articleFaveriteNo;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 收藏次数
     */
    private Integer  articleFaverRate;
    /**
     * 文章分类id
     */
    private String articleTypeId;
}
