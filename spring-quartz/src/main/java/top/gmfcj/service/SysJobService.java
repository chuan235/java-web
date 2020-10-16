package top.gmfcj.service;

import top.gmfcj.bean.SysJob;

import java.util.List;

// 任务调度器接口
public interface SysJobService{

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    public boolean checkCronExpressionIsValid(String cronExpression);


    public List<SysJob> queryAllJobs();
}
