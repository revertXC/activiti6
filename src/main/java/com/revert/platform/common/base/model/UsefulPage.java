package com.revert.platform.common.base.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UsefulPage<T> implements Serializable {

    /**
     * 页码，从1开始
     */
    private Integer pageNum;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 总数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 结果
     */
    private List<T> data;

    public UsefulPage pageNum(Integer pageNum){
        this.pageNum = pageNum;
        return this;
    }
    public UsefulPage pageSize(Integer pageSize){
        this.pageSize = pageSize;
        return this;
    }
    public UsefulPage total(Long total){
        this.total = total;
        return this;
    }
    public UsefulPage pages(Integer pages){
        this.pages = pages;
        return this;
    }
    public UsefulPage data(List data){
        this.data = data;
        return this;
    }

}
