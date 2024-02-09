package top.lhit.myBlog.module.dto.base;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BasePageDto {
    /**
     * 当前页码 如果没传入就为1
     */
    @NotNull(message = "未获取到当前页码")
    private Integer pageNumber = 1 ;
    /**
     * 每页显示的数据量 如果没传入默认为20
     */
    private Integer pageSize = 20 ;



}
