package top.lhit.myBlog.module.dto.aticle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ArticleDto {

    /**
     * 文章id
     */
    @NotBlank
    private String articleId;

    /**
     * 文章标题
     */
    @NotBlank
    private String articleTitle;
    /**
     * 文章封面url
     */
    @NotBlank
    private String articleCoverUrl;

    /**
     * 文章分类id
     */
    @NotBlank
    private String articleTypeId;
    /**
     * 文章标签 需要处理
     *
     */
    @NotBlank
    private String articleTagStr;
    /**
     * 文章添加时间
     */
    @NotNull
    private Date articleAddTime;
    /**
     * 文章内容
     */
    @NotBlank
    private String articleContext;
}
