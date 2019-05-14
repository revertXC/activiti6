package com.revert.platform.common.base.mapper;

import com.revert.platform.common.base.model.BaseEntity;

import java.util.List;

public interface BaseMapper<T extends BaseEntity> {

    /**
     * 查询多个
     * @param t
     * @return
     */
    List<T> selectByProperties(T t);

    /**
     * 根据ID删除元素
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加
     * @param model
     * @return
     */
    int insert(T model);

    /**
     * 非null添加
     * @param model
     * @return
     */
    int insertSelective(T model);

    /**
     * 根据ID查询对象
     * @param id
     * @return
     */
    T selectByPrimaryKey(Long id);

    /**
     * 非null修改
     * @param model
     * @return
     */
    int updateByPrimaryKeySelective(T model);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    int updateByDeleteId(Long id);

}
