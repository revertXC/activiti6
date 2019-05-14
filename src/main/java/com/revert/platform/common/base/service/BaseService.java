package com.revert.platform.common.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.revert.platform.common.annotation.LogicDelete;
import com.revert.platform.common.base.mapper.BaseMapper;
import com.revert.platform.common.base.model.BaseEntity;
import com.revert.platform.common.base.model.UsefulPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseService<Mapper extends BaseMapper<T>, T extends BaseEntity> {

    @Autowired
    protected Mapper mapper;

    public UsefulPage<T> selectByPage(T t){
        PageHelper.startPage(t.getPageNum(), t.getPageSize(), t.getOrderBy());
        Page<T> page = (Page<T>) this.selectByProperties(t);

        UsefulPage<T> resultData = new UsefulPage<>()
                .pageNum(page.getPageNum())
                .pageSize(page.getPageSize())
                .pages(page.getPages())
                .data(page.getResult())
                .total(page.getTotal());
        return resultData;
    }

    public List<T> selectByProperties(T t){
        return mapper.selectByProperties(t);
    }

    public void deleteById(Long id){
        Type superType = getClass().getGenericSuperclass();
        ParameterizedType parameterized = (ParameterizedType) superType;
        // 数据访问Service实现BaseService时，在其类定义中声明了两个泛型类，第一个是Mapper，第二个是Entity
        Type entityType = parameterized.getActualTypeArguments()[1];
        Class clazz = null;
        try {
            clazz = Class.forName(entityType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LogicDelete logicDelete = null;
        try{
            logicDelete = (LogicDelete) clazz.getDeclaredAnnotation(LogicDelete.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(logicDelete != null && logicDelete.value()){
            mapper.updateByDeleteId(id);
        }else{
            mapper.deleteByPrimaryKey(id);
        }

    }

    public void save(T t){
        mapper.insert(t);
    }

    public void saveNotNull(T t){
        t.insertPre();
        mapper.insertSelective(t);
    }
}
