package com.jenkins.ext.jvm.entity.query;

import com.jenkins.ext.jvm.entity.BaseEntity;
import com.jenkins.ext.jvm.entity.TaskEntity;

/**
 * @author huangchengqian
 * @date 2020-12-15 11:55
 **/
public class QueryTask extends BaseEntity {
    private String name;
    private int type = TaskEntity.START_TYPE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
