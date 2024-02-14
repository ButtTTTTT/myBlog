package top.lhit.myBlog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication//springboot项目
@EnableScheduling//开启定时任务
@ServletComponentScan//开启servlet组件扫描
public class Generator {
    public static void main(String[] args) {
        SpringApplication.run(Generator.class, args);
    }
}
