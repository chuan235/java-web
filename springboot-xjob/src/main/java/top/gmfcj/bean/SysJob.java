package top.gmfcj.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.gmfcj.common.constants.ScheduleConstants;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Table(name = "sys_job")
@Entity
public class SysJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务Id
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 定时任务名称
     */
    @Column(name = "job_name", length = 64)
    private String jobName;
    /**
     * 定时任务组
     */
    @Column(name = "job_group", length = 64)
    private String jobGroup;
    /**
     * 定时任务全类名
     */
    @Column(name = "job_class_name", length = 64)
    private String jobClassName;

    /**
     * 定时任务方法名
     */
    @Column(name = "method_name", length = 64)
    private String methodName;
    /**
     * 定时任务方法参数
     */
    @Column(name = "method_params", length = 64)
    private String methodParams;
    /**
     * cron 表达式
     */
    @Column(name = "cron_expression", length = 128)
    private String cronExpression;

    /**
     * cron计划策略
     */
    @Column(name = "misfire_policy", length = 1)
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;
    /**
     * 定时任务状态 0=正常,1=暂停
     */
    @Column(name = "status", length = 1)
    private String status;

    /**
     * 是否并发执行 0=允许,1=禁止
     */
    @Column(name = "concurrent", length = 1)
    private String concurrent;

}
