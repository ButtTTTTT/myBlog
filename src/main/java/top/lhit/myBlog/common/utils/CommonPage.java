package top.lhit.myBlog.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页数据封装类
 * Created by bmlt on 2019/4/19.
 */
@Data
public class CommonPage<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<T>();
        Page<T> pageInfo = new Page<T>().setRecords(list);
        result.setTotalPage((int)pageInfo.getPages());
        result.setPageNumber((int)pageInfo.getCurrent());
        result.setPageSize((int)pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getRecords());
        return result;
    }

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(IPage<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage((int)pageInfo.getPages());
        result.setPageNumber((int)pageInfo.getCurrent());
        result.setPageSize((int)pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getRecords());
        return result;
    }

}
