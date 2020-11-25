package top.gmfcj.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.gmfcj.bean.SysJob;
import top.gmfcj.common.exception.TaskException;
import top.gmfcj.common.util.CronUtils;
import top.gmfcj.common.util.ScheduleUtils;
import top.gmfcj.common.constants.ScheduleConstants;
import top.gmfcj.dao.SysJobRepository;
import top.gmfcj.service.ISysJobService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class SysJobServiceImpl implements ISysJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobRepository jobRepository;

    // 初始化定时器
    @PostConstruct
    public void init() throws TaskException, SchedulerException {
        List<SysJob> jobs = jobRepository.findAll();
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
    public List<SysJob> queryList() {
        return jobRepository.findAll();
    }

    @Override
    public void run(Long jobId) throws SchedulerException {
        Optional<SysJob> optional = jobRepository.findById(jobId);
        if (optional.isPresent()) {
            ScheduleUtils.run(scheduler, optional.get());
        } else {
            throw new RuntimeException("未找到id对应的job数据...");
        }
    }

    @Override
    public void runOnce(Long jobId) throws SchedulerException {
        Optional<SysJob> optional = jobRepository.findById(jobId);
        if (optional.isPresent()) {
            ScheduleUtils.runOnce(optional.get());
        } else {
            throw new RuntimeException("未找到id对应的job数据...");
        }
    }

    @Override
    public int changeStatus(SysJob job) throws SchedulerException {
        Optional<SysJob> optional = jobRepository.findById(job.getId());
        if (!optional.isPresent()) {
            return -1;
        }
        // 数据库job
        SysJob dbJob = optional.get();
        // 传入的status参数
        String status = job.getStatus();
        int rows = 0;
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(dbJob);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(dbJob);
        }
        return rows;
    }


    @Override
    @Transactional
    public int resumeJob(SysJob job) throws SchedulerException {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        jobRepository.save(job);
        ScheduleUtils.resumeJob(scheduler, job.getId());
        return 1;

    }

    @Override
    @Transactional
    public int pauseJob(SysJob job) throws SchedulerException {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        job = jobRepository.saveAndFlush(job);
        ScheduleUtils.pauseJob(scheduler, job.getId());
        return 1;
    }
}
