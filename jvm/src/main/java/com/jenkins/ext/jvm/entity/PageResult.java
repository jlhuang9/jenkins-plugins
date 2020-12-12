package com.jenkins.ext.jvm.entity;


import java.util.List;

/**
 * @author huangchengqian
 * @date 2020-12-10 09:48
 */

public class PageResult<T> extends BaseEntity {

    private Long total;
    private List<T> rows;
    private Long pageSize = 10L;
    private Long pageIndex = 1L;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows, Long pageSize, Long pageIndex) {
        this.total = total;
        this.rows = rows;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }
}
