package top.lhit.myBlog.framework.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import top.lhit.myBlog.framework.filter.InvalidCookieFilter;
@Configuration
@Slf4j
public class BlogFiterConfig {

    @Autowired
    private InvalidCookieFilter invalidCookieFilter;

    /**
     * 请求之前处理 cookies
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean invalidCookiedClean() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(invalidCookieFilter);

        filterRegistrationBean.addUrlPatterns("/*");

        log.info("FilterRegistrationBean.invalidCookiedClean  ===> 开始对请求过滤无效的cookie");

        return filterRegistrationBean;

    }

}