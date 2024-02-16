package top.lhit.myBlog;
import com.alibaba.druid.filter.config.ConfigTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication//springboot项目
@EnableScheduling//开启定时任务
@ServletComponentScan//开启servlet组件扫描
@Slf4j
public class Generator {
    public static void main(String[] args) {

        SpringApplication.run(Generator.class, args);


        String publickey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ23ID2Ao3BRDbwfVdQF/O6DaLQYugKEOz2EG8hKqrdlNNbrr2sSEnQ3nlr/YwA3sS+SIpuwlySDOuy3K3ozF+ECAwEAAQ==";
        String password = "PUhXybNA/HI4dPWW/xael86xKRpgj/4y/sTOZQ4QVpdN1kcT7WCUfO2WlhlaDuABgHgazNiYVu/rPd0Xq/o7Zg==";
        try {
            log.info("password {}", ConfigTools.decrypt(publickey, password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
