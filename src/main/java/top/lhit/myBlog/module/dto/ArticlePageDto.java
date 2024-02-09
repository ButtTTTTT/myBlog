package top.lhit.myBlog.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.lhit.myBlog.module.dto.base.BasePageDto;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ArticlePageDto extends BasePageDto {
    /**
     * 文章标题
     */
    private String articleTitle;


}
