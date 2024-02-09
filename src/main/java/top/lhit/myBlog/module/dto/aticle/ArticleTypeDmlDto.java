package top.lhit.myBlog.module.dto.aticle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTypeDmlDto {
    /**
     * 文章分类id
     */
    @NotBlank(message = "文章分类id 不能为空")
    private String articleTypeId;

    /**
     * 文章分类名称
     */
    private String articleTypeName;

    /**
     * 文章分类排序，越小越靠前
     */
    private Integer articleTypeSort;

    /**
     * 文章分类父id
     */
    private String articleTypeParentId;


}
