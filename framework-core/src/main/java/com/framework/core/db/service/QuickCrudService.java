package com.framework.core.db.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.framework.core.db.bean.ListDataWrap;
import com.framework.core.db.bean.Page;
import com.framework.core.db.dao.QuickCrudDao;

@Service
public class QuickCrudService {
 
    private static final Logger LOGGER = LoggerFactory.getLogger( QuickCrudService.class );
    
    @Resource
    private QuickCrudDao quickCrudDao;
    
    @Transactional
    public Number create( Object pojo ) {
        return quickCrudDao.create( pojo );
    }
    @Transactional
    public int[] createBatch( List<? extends Object> pojos ) {
        return quickCrudDao.createBatch( pojos );
    }
    @Transactional
    public int updateById(Object pojo){
        return quickCrudDao.updateById( pojo );
    }
    @Transactional
    public int updateByObj(Object pojo, Object condition){
        return quickCrudDao.updateByObj( pojo, condition );
    }
    @Transactional
    public int updateByWhereStr(Object pojo, String whereStr, Object condition){
        return quickCrudDao.updateByObj( pojo, whereStr, condition );
    }
    
    /**
     * 通过id获取对象
     * @param clazz
     * @param id
     * @return 如果获取不到，则为null，如果有多个，获取第一个，并且打印警告日志
     */
    public <T>T getById(Class<T> clazz, Serializable id) {
        return quickCrudDao.getById( clazz, id );
    }
    /**
     * 获取数据量
     * @param clazz
     * @param condition对象
     * @return
     */
    public long count(Class clazz){
        return quickCrudDao.count( clazz );
    }
    public long count(Class clazz, Object condition){
        return quickCrudDao.count( clazz, condition );
    }
    /**
     * 获取数据条数
     * @param clazz
     * @param whereStr 除where。如： xxx=:xxx and yyy=:yyy 
     * @param condition对象，属性必须与whereStr参数名称一致
     * @return
     */
    public long count(Class clazz, String whereStr, Object condition){
        return quickCrudDao.count( clazz, whereStr, condition );
    }

    /**
     * 获取列表数据
     * @param clazz
     * @param condition
     * @return
     */
    public <T>List<T> getList(Class<T> clazz){
        return quickCrudDao.getList( clazz );
    }
    public <T>List<T> getList(Class<T> clazz, Object condition){
        return quickCrudDao.getList( clazz, condition );
    }
    public <T>List<T> getList(Class<T> clazz, String whereStr, Object condition){
        return quickCrudDao.getList( clazz, whereStr, condition );
    }
    
    public <T>ListDataWrap<T> getList(Class<T> clazz, Page page){
        return quickCrudDao.getList( clazz, page );
    }
    public <T>ListDataWrap<T> getList(Class<T> clazz, Object condition, Page page){
        return quickCrudDao.getList( clazz, condition, page );
    }
    public <T>ListDataWrap<T> getList(Class<T> clazz,  Object condition, Page page,String orderBy){
        return quickCrudDao.getList( clazz, condition, page, orderBy);
    }
    public <T>ListDataWrap<T> getList(Class<T> clazz, String whereStr, Object condition, Page page){
        return quickCrudDao.getList( clazz, whereStr, condition, page );
    }
    public <T>ListDataWrap<T> getList(Class<T> clazz, String whereStr, Object condition, Page page,String orderBy){
    	return quickCrudDao.getList( clazz, whereStr, condition, page ,orderBy);
    }
    
}
