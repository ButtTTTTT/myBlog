package top.lhit.myBlog.module.entity;

import java.time.LocalDateTime;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "article_tag_id")
    private String articleTagId;

    private String articleTagName;

    private Date articleTagAddTime;


}
