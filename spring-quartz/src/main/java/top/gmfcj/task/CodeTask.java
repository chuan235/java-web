package top.gmfcj.task;

import org.springframework.stereotype.Component;

/**
 * @description: 定时任务调度测试
 */
@Component("codeTask")
public class CodeTask {

    public void printJob() {
        System.out.println(Thread.currentThread().getName() + " print job 运行中.............");
    }
}
