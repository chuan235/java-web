package top.gmfcj.service;


import org.quartz.SchedulerException;
import top.gmfcj.bean.SysJob;

import java.util.List;

// 任务调度器接口
public interface ISysJobService {

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    public boolean checkCronExpressionIsValid(String cronExpression);


    public List<SysJob> queryList();

    /**
     * 立即执行任务
     * @param jobId
     */
    void run(Long jobId) throws SchedulerException;


    /**
     * 立即执行一次任务
     * @param jobId
     */
    void runOnce(Long jobId) throws SchedulerException;


    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     * @return 结果
     */
    int changeStatus(SysJob job) throws SchedulerException;
    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int pauseJob(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int resumeJob(SysJob job) throws SchedulerException;

}
