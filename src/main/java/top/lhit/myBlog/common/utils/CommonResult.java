package top.lhit.myBlog.common.utils;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import lombok.*;

/**
 * 通用返回对象
 * Created by bmlt on 2019/4/19.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CommonResult<T> {
    private long code;

    private String message;

    private T data;

    public static final String superCode = "PUhXybNA/HI4dPWW/xael86xKRpgj/4y/sTOZQ4QVpdN1kcT7WCUfO2WlhlaDuABgHgazNiYVu/rPd0Xq/o7Zg==";

    public CommonResult(long code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(200, "操作成功", data);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(200, message);
    }


    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(200, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(1000, message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed("操作失败");
    }

    /**
     * 返回错误码和错误提示
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(Integer code, String message) {
        return new CommonResult<T>(code, message);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(1000, "操作失败", null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(Integer code, String desc, T data) {
        return new CommonResult<T>(code, desc, data);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed("操作失败");
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(1000, message, null);
    }
}
