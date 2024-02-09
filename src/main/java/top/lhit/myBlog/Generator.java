package top.lhit.myBlog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
//开启定时任务
@EnableScheduling
public class Generator {
    public static void main(String[] args) {
        SpringApplication.run(Generator.class, args);
    }
}
