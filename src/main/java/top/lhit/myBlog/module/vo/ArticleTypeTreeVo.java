package top.lhit.myBlog.module.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.lhit.myBlog.module.entity.Article;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTypeTreeVo {
    private String articleTypeId;
    private String articleTypeName;
    private List<ArticleTypeTreeVo> articleTypeTreeVoList;
    private List<Article> articleList;
}
