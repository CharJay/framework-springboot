package com.framework.core.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.db.dao.QuickSimpleDao;

@Service
public class QuickSimpleService {
 
    private static final Logger LOGGER = LoggerFactory.getLogger( QuickSimpleService.class );
    
    @Resource
    private QuickSimpleDao quickSimpleDao;
    
    /**
     * 执行更新操作
     * @param sql
     * @param param
     * @return
     */
    @Transactional
    public int update(String sql){
        return quickSimpleDao.update( sql );
    }
    @Transactional
    public int update(String sql, Object param){
        return quickSimpleDao.update( sql, param );
    }
    @Transactional
    public int update(String sql, Map<String, Object> param ){
        return quickSimpleDao.update( sql, param );
    }
    
    /**
     * 批量更新，sql只有一条。
     * @param sql
     * @param param
     * @return
     */
    @Transactional
    public int[] updateBatByObj(String sql, List<? extends Object> param ){
        return quickSimpleDao.updateBat( sql, param );
    }
    @Transactional
    public int[] updateBatByMap(String sql, List<Map<String,Object>> param ){
        List pp = param;
        return quickSimpleDao.updateBat( sql, pp  );
    }
    
    /**
     * 批量更新，多条sql，用于多sql需要事务处理
     * @param params
     * @return
     */
    @Transactional
    public int[] updateBatByObj(List<UpdateBatParamObj> params ){
        int[] ret = new int[params.size()];
        for (int i = 0; i < params.size(); i++) {
            UpdateBatParamObj p = params.get( i );
            ret[i] = quickSimpleDao.update( p.sql, p.param );
        }
        return ret;
    }
    @Transactional
    public int[] updateBatByMap(List<UpdateBatParamMap> params){
        int[] ret = new int[params.size()];
        for (int i = 0; i < params.size(); i++) {
            UpdateBatParamMap p = params.get( i );
            ret[i] = quickSimpleDao.update( p.sql, p.param );
        }
        return ret;
    }
    
    /**
     * 返回 数值型，用于查询统计计数
     * @param sql
     * @param param
     * @return
     */
    public long queryForLong(String sql){
        return quickSimpleDao.queryForLong( sql );
    }
    public String queryForString(String sql){
        return quickSimpleDao.queryForString( sql );
    }
    public long queryForLong(String sql, Map<String, Object> param){
        return quickSimpleDao.queryForLong( sql, param );
    }
    public long queryForLong(String sql, Object param){
        return quickSimpleDao.queryForLong( sql, param );
    }
    public String queryForString(String sql, Map<String, Object> param){
        return quickSimpleDao.queryForString( sql, param );
    }
    public String queryForString(String sql, Object param){
        return quickSimpleDao.queryForString( sql, param );
    }
    
    /**
     * 返回map列表
     * @param sql
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryForList(String sql){
        return quickSimpleDao.queryForList( sql );
    }
    public List<Map<String,Object>> queryForList(String sql, Map<String, Object> param){
        return quickSimpleDao.queryForList( sql, param );
    }
    public List<Map<String,Object>> queryForList(String sql, Object param){
        return quickSimpleDao.queryForList( sql, param );
    }
    public <T>List<T> queryForList(String sql,  Class<T> clazz){
        return quickSimpleDao.queryForList( sql, clazz );
    }
    public <T>List<T> queryForList(String sql, Map<String, Object> param, Class<T> clazz){
        return quickSimpleDao.queryForList( sql, param, clazz );
    }
    public <T>List<T> queryForList(String sql, Object param, Class<T> clazz){
        return quickSimpleDao.queryForList( sql, param, clazz );
    }
    
    public  <T>T queryForObject(String sql, Class<T> clazz){
        return quickSimpleDao.queryForObject( sql, clazz );
    }
    public  <T>T queryForObject(String sql, Object param, Class<T> clazz){
        return quickSimpleDao.queryForObject( sql, param, clazz );
    }

    public Map<String, Object> queryForObjectFirst(String sql, Map<String, Object> param) {
        return quickSimpleDao.queryForObjectFirst(sql, param);
    }
    ///////////
    public static class UpdateBatParamMap{
        public String sql;
        public Map<String, Object> param=new HashMap<String, Object>();
    }
    public static class UpdateBatParamObj{
        public String sql;
        public Object param;
    }
    
    //=========================================================
	@Transactional
	public int[] batchUpdate(String sql, List<? extends Map<String, Object>> params) {
		return quickSimpleDao.batchUpdate(sql, params);
	}

	public long count(String sql, Map<String, Object> param) {
		return quickSimpleDao.count(sql, param);
	}

	public <T> List<T> queryLang(String sql, Map<String, Object> param, Class<T> retClass) {
		return quickSimpleDao.queryLang(sql, param, retClass);
	}
	
	public <T> T queryLangFirst(String sql, Map<String, Object> param, Class<T> retClass) {
		return quickSimpleDao.queryLangFirst(sql, param, retClass);
	}

	public <T> List<T> query(String sql, Map<String, Object> param, Class<T> retClass) {
		return quickSimpleDao.query(sql, param, retClass);
	}

	public <T> T queryFirst(String sql, Map<String, Object> param, Class<T> retClass) {
		return quickSimpleDao.queryFirst(sql, param, retClass);
	}

	public List<Map<String, Object>> queryMap(String sql, Map<String, Object> param) {
		return quickSimpleDao.queryMap(sql, param);
	}
}
