package top.lhit.myBlog.module.entity;
import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 友情链接id
     */
    @TableId(value = "link_id")
    private String linkId;
    /**
     * 友情链接地址
     */
    private String linkUrl;
    /**
     * 友情链接标题
     */
    private String linkTitle;
    /**
     * 友情链接logo url
     */
    private String linkLogoUrl;
    /**
     * 友情链接的排序 越小越靠前
     */
    private Integer linkSort;
    /**
     * 添加友情链接的时间
     */
    private Date linkAddTime;
}
