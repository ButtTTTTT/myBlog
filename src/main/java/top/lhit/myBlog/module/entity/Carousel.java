package top.lhit.myBlog.module.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "carousel")
@AllArgsConstructor
@NoArgsConstructor
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;
    //轮播图id
    @TableId(value = "carousel_id")
    private String carouselId;
    //轮播图排序
    private Integer carouselSort;
    //轮播图图片地址
    private String carouselImageUrl;
    //轮播图标题
    private String carouselTitle;
    //轮播图内容
    private String carouselContent;
    //轮播图是否启用
    private Integer carouselIsActive;
    //轮播图位置
    private String carouselTarget;
    //轮播图创建时间
    private Date carouselCreateTime;
}
