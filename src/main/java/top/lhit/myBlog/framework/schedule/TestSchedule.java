//package top.lhit.myBlog.framework.schedule;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;
//import java.util.concurrent.ScheduledFuture;
//@Component
//@Slf4j
//@PropertySource(value = "file:D:\\logs\\config.properties")
//public class TestSchedule {
//    @Autowired
//    private TaskScheduler taskScheduler;
//
//    private ScheduledFuture<?> scheduledFuture;
//
//    private String cron;
//
//    public static String filepath;
//
//    @Value("${outPutPath}")
//    public void setFilepath(String filepath) {
//        TestSchedule.filepath = filepath;
//    }
//
//    @Value("${schecron}")
//    public void setCron(String cron) {
//        this.cron = cron;
//
//        //取消之前的定时任务
//        if (scheduledFuture != null) {
//            scheduledFuture.cancel(true);
//        }
//
//        //使用新的cron表达式创建新的定时任务
//        scheduledFuture = taskScheduler.schedule(this::execute, new CronTrigger(cron));
//    }
//
//    @PostConstruct
//    public void init() {
//        if (cron == null) {
//            throw new IllegalStateException("请重新设置定时任务!");
//        }
//        // 初始设置的定时任务
//        scheduledFuture = taskScheduler.schedule(this::execute, new CronTrigger(cron));
//    }
//
//    public void execute() {
//
//    }
//}
