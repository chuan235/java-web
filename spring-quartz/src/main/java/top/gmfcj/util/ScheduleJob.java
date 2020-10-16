package top.gmfcj.util;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import top.gmfcj.bean.SysJob;
import top.gmfcj.config.SpringUtils;
import top.gmfcj.constants.ScheduleConstants;

import java.util.concurrent.Future;

/**
 * 定时任务处理
 */
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(ScheduleJob.class);

    private ThreadPoolTaskExecutor executor = SpringUtils.getBean("threadPoolTaskExecutor");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        SysJob job = new SysJob();
        BeanUtils.copyProperties(job, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
        long startTime = System.currentTimeMillis();
        try {
            // 执行任务
            log.info("任务开始执行 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
            ScheduleRunnable task = new ScheduleRunnable(job.getJobName(), job.getMethodName(), job.getMethodParams());
            Future<?> future = executor.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            log.info("任务执行结束 - 名称：{} 耗时：{} 毫秒", job.getJobName(), times);
        } catch (Exception e) {
            log.info("任务执行失败 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
            log.error("任务执行异常  - ：", e);
        }
    }
}
