package com.jenkins.ext.jvm.controller;

import com.jenkins.ext.jvm.entity.PageResult;
import com.jenkins.ext.jvm.entity.ResultDto;
import com.jenkins.ext.jvm.entity.TaskEntity;
import com.jenkins.ext.jvm.service.TaskService;
import com.jenkins.ext.jvm.utils.RestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangchengqian
 * @date 2020-12-11 08:50
 **/
@RestController
@RequestMapping("report")
public class ReportController {

    @Resource
    private TaskService taskService;


    @GetMapping("pageQuery")
    public ResultDto<?> pageQuery(PageResult pageResult) {
        return RestUtils.sucess(taskService.pageWorkspace(pageResult));
    }

    @GetMapping("pageTaskQuery")
    public ResultDto<?> pageTaskQuery(PageResult pageResult, TaskEntity taskEntity) {
        return RestUtils.sucess(taskService.pageTask(pageResult, taskEntity));
    }

    @GetMapping("getConsoleLog")
    public ResultDto<?> getConsole(TaskEntity taskEntity) {
        return RestUtils.sucess(taskService.getConsole(taskEntity));
    }
}
