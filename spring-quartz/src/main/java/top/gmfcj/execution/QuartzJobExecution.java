package top.gmfcj.execution;

import org.quartz.JobExecutionContext;
import top.gmfcj.bean.SysJob;
import top.gmfcj.util.JobInvokeUtil;

/**
 * 定时任务处理（允许并发执行）
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
