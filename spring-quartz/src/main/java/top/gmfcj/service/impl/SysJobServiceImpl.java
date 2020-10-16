package top.gmfcj.service.impl;


import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import top.gmfcj.bean.SysJob;
import top.gmfcj.exception.TaskException;
import top.gmfcj.service.SysJobService;
import top.gmfcj.util.CronUtils;
import top.gmfcj.util.ScheduleUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SysJobServiceImpl implements SysJobService {

    @Autowired
    private Scheduler scheduler;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    // 初始化定时器
    @PostConstruct
    public void init() throws TaskException, SchedulerException {
        List<SysJob> jobs = this.queryAllJobs();
        for (SysJob job : jobs) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getId());
            // 如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }

    }



    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }

    @Override
    public List<SysJob> queryAllJobs() {
        List<SysJob> list = jdbcTemplate.query("select * from sys_job", new SysJob());
        return list;
    }
}
