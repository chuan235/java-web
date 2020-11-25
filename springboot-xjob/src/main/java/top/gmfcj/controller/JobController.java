package top.gmfcj.controller;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.gmfcj.bean.SysJob;
import top.gmfcj.common.base.AjaxResult;
import top.gmfcj.common.base.BaseController;
import top.gmfcj.service.ISysJobService;

@Slf4j
@RequestMapping("/sys/job")
@Controller
public class JobController extends BaseController {

    @Autowired
    private ISysJobService jobService;


    @GetMapping("/list")
    @ResponseBody
    public AjaxResult jobs(){
        return toAjax(jobService.queryList());
    }

    // 触发单个任务 触发后一直执行
    @PostMapping("/run/{jobId}")
    @ResponseBody
    public AjaxResult runJob(@PathVariable Long jobId){
        try {
            jobService.run(jobId);
        }catch (SchedulerException e) {
            log.error(e.getMessage());
            return AjaxResult.error("执行错误！");
        }
        return AjaxResult.success();
    }

    // 触发单个任务 只执行一次
    @PostMapping("/runOnce/{jobId}")
    @ResponseBody
    public AjaxResult runOnce(@PathVariable Long jobId){
        try {
            jobService.runOnce(jobId);
        }catch (SchedulerException e) {
            log.error(e.getMessage());
            return AjaxResult.error("执行错误！");
        }
        return AjaxResult.success();
    }

    // 暂停/恢复 任务
    @PostMapping("/change")
    @ResponseBody
    public AjaxResult changeJobStatus(SysJob job){
        try {
            jobService.changeStatus(job);
        }catch (SchedulerException e) {
            log.error(e.getMessage());
            return AjaxResult.error("执行错误！");
        }
        return AjaxResult.success();
    }

    // 增 删  改

}
