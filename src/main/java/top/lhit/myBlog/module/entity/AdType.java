package top.lhit.myBlog.module.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
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
public class AdType implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 广告类型id
     */
    @TableId(value = "ad_type_id")
    private String adTypeId;

    /**
     * 广告类型名称
     */
    private String adTypeTitle;

    /**
     * 广告类型标识（首页顶部广告，轮播图广告，文章详情广告）
     */
    private String adTypeTag;

    /**
     * 广告类型排序，越小越靠前
     */
    private Integer adTypeSort;

    /**
     * 广告类型添加时间
     */
    private Date adTypeAddTime;


}
