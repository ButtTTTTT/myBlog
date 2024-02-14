package top.lhit.myBlog.framework.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class InvalidCookieFilter implements Filter {
    /**
     * 过滤请求中cookie
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("InvalidCookieFilter ==> 开始对请求进行预处理");
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {

                //遍历cookie 并清空无效Cookie
                if (isInvalid(cookie)) {

                    log.info("Cookie To Delete ===>  {}", cookie);

                    cookie.setMaxAge(0);

                    cookie.setPath(request.getContextPath());

                    response.addCookie(cookie);

                }

            }

        }


        filterChain.doFilter(servletRequest, servletResponse);

    }

    /**
     * 判断cookie是否有效
     *
     * @param cookie
     * @return
     */
    private boolean isInvalid(Cookie cookie) {
        /**
         * cookie 判断逻辑
         *
         *
         *
         */


        return false;
    }

    @Override
    public void destroy() {
        log.info("InvalidCookieFilter ==>  已销毁");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("初始化过滤器{}", filterConfig.getFilterName());
    }
}
