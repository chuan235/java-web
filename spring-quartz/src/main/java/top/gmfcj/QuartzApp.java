package top.gmfcj;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import top.gmfcj.config.AppConfig;

import java.util.concurrent.CountDownLatch;

/**
 * @description: main start
 * @author: GMFCJ
 * @create: 2020-10-16 09:15
 */
public class QuartzApp {

    public static void main(String[] args) throws InterruptedException {
        // 启动app
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        new CountDownLatch(1).await();
    }


}
