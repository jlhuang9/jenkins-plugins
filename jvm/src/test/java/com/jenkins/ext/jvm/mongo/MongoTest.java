package com.jenkins.ext.jvm.mongo;

import com.alibaba.fastjson.JSONObject;
import com.jenkins.ext.jvm.BaseTest;
import com.jenkins.ext.jvm.entity.PageResult;
import com.jenkins.ext.jvm.entity.TaskEntity;
import com.jenkins.ext.jvm.entity.WorksapceEntity;
import com.jenkins.ext.jvm.service.TaskService;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangchengqian
 * @date 2020-12-08 14:58
 **/
public class MongoTest extends BaseTest {

    @Resource
    private TaskService taskService;

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void name() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName("nana");
        taskEntity.setLog("haha");
        taskEntity.setNumber(1);
        mongoTemplate.insert(taskEntity);
    }

    @Test
    public void testQueryAll() {
        List<TaskEntity> all = mongoTemplate.findAll(TaskEntity.class);
        System.out.println(JSONObject.toJSONString(all));
    }

    @Test
    public void testSave() {
        WorksapceEntity worksapceEntity = new WorksapceEntity();
        worksapceEntity.setName("haa");
        worksapceEntity.setUpdateTime(System.currentTimeMillis());
        mongoTemplate.save(worksapceEntity);
    }

    @Test
    public void testPage() {
        PageResult pageResult = new PageResult();
        PageResult<WorksapceEntity> page = taskService.pageWorkspace(pageResult);
        System.out.println(JSONObject.toJSONString(page));
    }
}