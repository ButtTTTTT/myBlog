package top.lhit.myBlog.module.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PublishArticleActionDto {

    /**
     * 文章id
     */
    private String articleId;
    /**
     * 文章标题
     */
    @NotBlank(message = "请填写文章标题")
    @Length(max = 480, message = "文章标题不能超过480个字符")
    private String articleTitle;
    /**
     * 文章类型id
     */
    @NotBlank(message = "请选择文章的类型")
    private String articleTypeId;
    /**
     * 文章封面url
     */
    private String articleCoverUrl;
    /**
     * 标签id列表
     */
    private String[] articleTagIds;
    /**
     * 文章内容
     */
    @NotBlank(message = "请填写文章内容")
    @Length(min = 5, max = 15000, message = "文章内容在5-15000字符之间")
    private String articleContext;

}
