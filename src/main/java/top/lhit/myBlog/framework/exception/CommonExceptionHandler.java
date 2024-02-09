package top.lhit.myBlog.framework.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lhit.myBlog.common.utils.CommonResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author M.y
 * @create 2020/1/4 15:46
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    /**
     * 一般的参数绑定时候抛出的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResult handleBindException(BindException ex) {
        List<String> defaultMsg = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return CommonResult.failed(defaultMsg.get(0));
    }

    /**
     * 参数校验异常获取
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return CommonResult.failed(e.getBindingResult().getFieldError().getDefaultMessage());
    }

}