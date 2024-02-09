package top.lhit.myBlog.module.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lhit.myBlog.module.entity.Ad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lhit.myBlog.module.vo.AdVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-11-29
 */@Mapper
public interface AdMapper extends BaseMapper<Ad> {
    /**
     * 广告列表，包含广告类型名称
     * @param adTypeId
     * @return
     */
    List<AdVo> adList(@Param("adTypeId") String adTypeId);

}
