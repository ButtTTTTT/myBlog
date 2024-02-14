package top.lhit.myBlog.framework.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import top.lhit.myBlog.framework.filter.InvalidCookieFilter;
@Configuration
public class BlogFiterConfig {
    @Autowired
    private InvalidCookieFilter invalidCookieFilter;

    @Bean
    public FilterRegistrationBean thirdFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(invalidCookieFilter);

        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

}