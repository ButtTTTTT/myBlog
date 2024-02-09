package top.lhit.myblog;

import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class tt {
    @Test
    public void test01(){
        System.out.println(SecureUtil.md5("UNKNNOW" + "UnKnnow@lhit.top419179"));
    }
}
