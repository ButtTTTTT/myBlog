package top.lhit.myBlog.framework.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 响应设置header 解决 unsafe-url 等跨域问题
 */
@Slf4j
public class ProcessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        boolean flag = true;

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");

        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

        httpServletResponse.setHeader("X-Powered-By", "Jetty");

        String method = httpServletRequest.getMethod();

        if (method.equals("OPTIONS")) {

            httpServletResponse.setStatus(200);

            return !flag;

        }

        log.info("ProcessInterceptor.HandlerInterceptor :  {}", flag);

        return flag;
    }

}

