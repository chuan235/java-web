package top.gmfcj.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import top.gmfcj.constants.ScheduleConstants;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
@Data
public class SysJob implements Serializable, RowMapper<SysJob> {

    private static final long serialVersionUID = 1L;

    @Override
    public SysJob mapRow(ResultSet rs, int i) throws SQLException {
        SysJob sysJob = new SysJob();
        sysJob.setId(rs.getLong("id"));
        sysJob.setJobName(rs.getString("job_name"));
        sysJob.setJobGroup(rs.getString("job_group"));
        sysJob.setJobClassName(rs.getString("job_class_name"));
        sysJob.setMethodName(rs.getString("method_name"));
        sysJob.setMethodParams(rs.getString("method_params"));
        sysJob.setCronExpression(rs.getString("cron_expression"));
        sysJob.setMisfirePolicy(rs.getString("misfire_policy"));
        sysJob.setStatus(rs.getString("status"));
        sysJob.setConcurrent(rs.getString("concurrent"));
        return sysJob;
    }

    /**
     * 任务Id
     */
    private Long id;
    /**
     * 定时任务名称
     */
    private String jobName;
    /**
     * 定时任务组
     */
    private String jobGroup;
    /**
     * 定时任务全类名
     */
    private String jobClassName;

    /**
     * 定时任务方法名
     */
    private String methodName;
    /**
     * 定时任务方法参数
     */
    private String methodParams;
    /**
     * cron 表达式
     */
    private String cronExpression;

    /**
     * cron计划策略
     */
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;
    /**
     * 定时任务状态 0=正常,1=暂停
     */
    private String status;

    /**
     * 是否并发执行 0=允许,1=禁止
     */
    private String concurrent;

}
