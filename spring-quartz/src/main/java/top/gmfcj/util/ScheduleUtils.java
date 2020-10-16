package top.gmfcj.util;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.gmfcj.bean.SysJob;
import top.gmfcj.constants.ScheduleConstants;
import top.gmfcj.exception.TaskException;
import top.gmfcj.execution.QuartzDisallowConcurrentExecution;
import top.gmfcj.execution.QuartzJobExecution;
import top.gmfcj.schedule.ScheduleJob;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定时任务工具类
 */
public class ScheduleUtils {
    private static final Logger log = LoggerFactory.getLogger(ScheduleUtils.class);
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * 得到quartz任务类
     *
     * @param sysJob 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(SysJob sysJob) {
        boolean isConcurrent = "0".equals(sysJob.getConcurrent());
        // 使用quartz的线程池  => CodeScheduler_Worker-1
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
        // 使用自定义的线程池 => threadPoolTaskExecutor-1
//         return ScheduleJob.class;
    }

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            log.error("getCronTrigger 异常：", e);
        }
        return null;
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(getJobKey(job.getId()))
                .build();
        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);
        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(job.getId()))
                .withSchedule(cronScheduleBuilder)
                .build();
        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);
        scheduler.scheduleJob(jobDetail, trigger);
        // 暂停任务
        if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
            pauseJob(scheduler, job.getId());
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
        JobKey jobKey = getJobKey(job.getId());
        // 判断是否存在
        if (scheduler.checkExists(jobKey)) {
            // 先移除，然后做更新操作
            scheduler.deleteJob(jobKey);
        }
        createScheduleJob(scheduler, job);
        // 暂停任务
        if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
            pauseJob(scheduler, job.getId());
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, SysJob job) throws SchedulerException {
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, job);
        scheduler.triggerJob(getJobKey(job.getId()), dataMap);
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) throws SchedulerException {
        scheduler.pauseJob(getJobKey(jobId));
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) throws SchedulerException {
        scheduler.resumeJob(getJobKey(jobId));
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) throws SchedulerException {
        scheduler.deleteJob(getJobKey(jobId));
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)
            throws TaskException {
        switch (job.getMisfirePolicy()) {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }

    public static void copyBeanProp(Object dest, Object src) {
        List<Method> destSetters = getSetterMethods(dest);
        List srcGetters = getGetterMethods(src);
        try {
            Iterator it = destSetters.iterator();
            while (it.hasNext()) {
                Method setter = (Method) it.next();
                Iterator it2 = srcGetters.iterator();
                while (it2.hasNext()) {
                    Method getter = (Method) it2.next();
                    if (isMethodPropEquals(setter.getName(), getter.getName()) && setter.getParameterTypes()[0].equals(getter.getReturnType())) {
                        setter.invoke(dest, getter.invoke(src));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static List<Method> getSetterMethods(Object obj) {
        List<Method> setterMethods = new ArrayList();
        Method[] methods = obj.getClass().getMethods();
        Method[] ms = methods;
        int len = methods.length;

        for (int i = 0; i < len; ++i) {
            Method method = ms[i];
            java.util.regex.Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && method.getParameterTypes().length == 1) {
                setterMethods.add(method);
            }
        }

        return setterMethods;
    }

    public static List<Method> getGetterMethods(Object obj) {
        List<Method> getterMethods = new ArrayList();
        Method[] methods = obj.getClass().getMethods();
        Method[] ms = methods;
        int len = methods.length;

        for (int i = 0; i < len; ++i) {
            Method method = ms[i];
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && method.getParameterTypes().length == 0) {
                getterMethods.add(method);
            }
        }

        return getterMethods;
    }

    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(3).equals(m2.substring(3));
    }

}
