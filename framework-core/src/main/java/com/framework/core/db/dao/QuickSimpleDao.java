package com.framework.core.db.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QuickSimpleDao {
    
    private static final Logger        LOGGER = LoggerFactory.getLogger( QuickSimpleDao.class );
    
//    @Resource
//    private JdbcTemplate               jdbcTemplate;
    
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * @param sql
     * @return
     */
    public int update( String sql ) {
        
        LOGGER.debug( "sql:{} ", sql );
        int ret = jdbcTemplate.update(sql);
        //int   ret = namedParameterJdbcTemplate.update( sql, new HashMap<String,Object>() );
        
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    /**
     * 
     * @param sql
     * @param param : 可以是实体对象Object，或者是Map<String, Object>
     * @return
     */
    public int update( String sql, Object param ) {
        
        //        String sql = "INSERT INTO employees(last_name, email,dept_id) VALUES(:lastName, :email,:deptid)";  
        //        Employee employee = new Employee();  
        //        employee.setLastName("Jack");  
        //        employee.setEmail("Jack@163.com");  
        //        employee.setDeptid(3);  
        
        //        String sql = "insert into sb_teacher (age, create_time, memo, name) values (:age, :createTime, :memo, :name)";  
        //        Map<String, Object> param = new HashMap<>();  
        //        param.put("age", 18);  
        //        param.put("createTime", new Date());  
        //        param.put("memo", "备注！！");  
        //        param.put("name", "测试名称");
        
        LOGGER.debug( "sql:{} ; param:{}", sql, param );
        int ret = 0;
        
        if( param instanceof Map){
            Map<String,Object> pp = (Map<String, Object>) param;
            ret = namedParameterJdbcTemplate.update( sql, pp );
        }else{
            ret = namedParameterJdbcTemplate.update( sql, new BeanPropertySqlParameterSource( param ) );
            
        }
        
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    
    /**
     * 
     * @param sql
     * @param param : List中的泛型可以是实体对象Object，或者是Map<String, Object>
     * @return
     */
    public int[] updateBat( String sql, List<? extends Object> param ) {
        
        //        String sql = "INSERT INTO employees(last_name, email,dept_id) VALUES(:lastName, :email,:deptid)";  
        //        Employee employee1 = new Employee("qq", "qq@qq.com",1);  
        //        Employee employee2 = new Employee("ww", "ww@qq.com",4);  
        //        Employee employee3 = new Employee("ee", "ee@qq.com",1);  
        //        SqlParameterSource[] batchArgs = new SqlParameterSource[]{  
        //                new BeanPropertySqlParameterSource(employee1),  
        //                new BeanPropertySqlParameterSource(employee2),  
        //                new BeanPropertySqlParameterSource(employee3),};  
        
        LOGGER.debug( "sql:{} ; param:{}", sql, param );
        int[] ret = null;
        
        if(param.size()==0) throw new RuntimeException("param参数为空");
        Object testobj = param.get( 0 );
        if( testobj instanceof Map){
            
            Map<String, Object>[] batchArgs = new Map[param.size()];
            for (int i = 0; i < param.size(); i++) {
                Object obj = param.get( i );
                batchArgs[i] = (Map<String, Object>) obj;
            }
            
            ret = namedParameterJdbcTemplate.batchUpdate( sql, batchArgs );
            
        }else{
            
            SqlParameterSource[] batchArgs = new SqlParameterSource[param.size()];
            for (int i = 0; i < param.size(); i++) {
                Object obj = param.get( i );
                batchArgs[i] = new BeanPropertySqlParameterSource( obj );
            }
            
            ret = namedParameterJdbcTemplate.batchUpdate( sql, batchArgs );
            
        }
        
        
        LOGGER.debug( "ret[]:{}", ret );
        
        return ret;
    }
    
    //返回数值型
    /**
     * 返回有且仅有一条数据，否则抛异常
     * @param sql
     * @param param : 可以是实体对象，或者是Map<String, Object>
     * @return
     */
    public long queryForLong(String sql)  {
        LOGGER.debug( "sql:{} ", sql );
        Number  number = namedParameterJdbcTemplate.queryForObject( sql, new HashMap<String,Object>(), Long.class );
        long ret = (number != null ? number.longValue() : 0);
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    public String queryForString(String sql) {
        LOGGER.debug( "sql:{} ", sql );
        String str = namedParameterJdbcTemplate.queryForObject( sql, new HashMap<String,Object>(), String.class );
        LOGGER.debug( "ret:{}", str );
        return str;
    }
    public String queryForString(String sql, Object param) {
        LOGGER.debug( "sql:{} ; param:{}", sql, param );
        String str = null;
        if( param instanceof Map){
            Map<String, Object> pp = (Map<String, Object>) param;
            str = namedParameterJdbcTemplate.queryForObject( sql, pp, String.class );
        }else{
            str = namedParameterJdbcTemplate.queryForObject( sql, new BeanPropertySqlParameterSource(param), String.class );
        }
        LOGGER.debug( "ret:{}", str );
        return str;
    }
    public long queryForLong(String sql, Object param)  {
        LOGGER.debug( "sql:{} ; param:{}", sql, param );
        Number number = null;
        if( param instanceof Map){
            Map<String, Object> pp = (Map<String, Object>) param;
            number = namedParameterJdbcTemplate.queryForObject( sql, pp, Long.class );
        }else{
            number = namedParameterJdbcTemplate.queryForObject( sql, new BeanPropertySqlParameterSource(param), Long.class );
        }
        long ret = (number != null ? number.longValue() : 0);
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }

    /**
     * 获取对象列表
     * @param sql
     * @param param : 可以是实体对象，或者是Map<String, Object>
     * @return
     */
    public List<Map<String,Object>> queryForList( String sql) {
        LOGGER.debug( "sql:{} ", sql );
        
        List<Map<String, Object>> ret = namedParameterJdbcTemplate.queryForList( sql, new HashMap<String,Object>() );
        LOGGER.debug( "ret.size:{}", ret.size());
        return ret;
    }
    public List<Map<String,Object>> queryForList( String sql, Object param) {
        LOGGER.debug( "sql:{} ; param:{}", sql, param );
        
        List<Map<String, Object>> ret = null;
        if( param instanceof Map){
            Map<String, Object> pp = (Map<String, Object>) param;
            ret = namedParameterJdbcTemplate.queryForList( sql, pp );
        }else{
            ret = namedParameterJdbcTemplate.queryForList( sql, new BeanPropertySqlParameterSource(param) );
        }
        
        
        LOGGER.debug( "ret.size:{}", ret.size());
        return ret;
    }
    
    public Map<String,Object> queryForObjectFirst( String sql, Object param) {
        List<Map<String, Object>> list = queryForList(sql, param);
        return list==null || list.size()==0 ? null : list.get( 0 );
    }
    
    /**
     * 获取对象列表
     * @param sql
     * @param param : 可以是实体对象，或者是Map<String, Object>
     * @return
     */
    public <T>List<T> queryForList( String sql, Class<T> clazz ) {
        LOGGER.debug( "sql:{} ;  clazz:{}", sql, clazz );
        
        List<T> ret = namedParameterJdbcTemplate.query( sql, new HashMap<String,Object>(),
                    new BeanPropertyRowMapper(clazz));
        
        LOGGER.debug( "ret.size:{}", ret.size());
        return ret;
    }
    public <T>List<T> queryForList( String sql, Object param, Class<T> clazz ) {
        LOGGER.debug( "sql:{} ; param:{}; clazz:{}", sql, param, clazz );
        
        List<T> ret = null;
        if( param instanceof Map){
            Map<String, Object> pp = (Map<String, Object>) param;
            ret = namedParameterJdbcTemplate.query( sql, pp,
                    new BeanPropertyRowMapper(clazz));
        }else{
            ret = namedParameterJdbcTemplate.query( sql, new BeanPropertySqlParameterSource(param),
                    new BeanPropertyRowMapper(clazz));
        }
        
        
        LOGGER.debug( "ret.size:{}", ret.size());
        return ret;
    }
    
    public Map<String,Object> queryForObject( String sql) {
        List<Map<String, Object>> list = queryForList(sql);
        return list==null || list.size()==0 ? null : list.get( 0 );
    }
    /**
     * 查询第一个对象。如果对象为空，返回null
     * @param sql
     * @param param : 可以是实体对象，或者是Map<String, Object>
     * @param clazz
     * @return
     */
    public <T>T queryForObject(String sql, Class<T> clazz ) {
        LOGGER.debug( "sql:{} ; clazz:{}", sql, clazz );
        
        List<T> list = queryForList( sql, clazz );
        
        LOGGER.debug( "ret:{}", list );
        
        return list==null || list.size()==0 ? null : list.get( 0 );
    }
    public <T>T queryForObject(String sql, Object param, Class<T> clazz ) {
        LOGGER.debug( "sql:{} ; param:{}; clazz:{}", sql,param, clazz );
        
        List<T> list = queryForList( sql,param, clazz );
        
        LOGGER.debug( "ret:{}", list );
        
        return list==null || list.size()==0 ? null : list.get( 0 );
    }
    
    //=============================================================
    
	public int update(String sql, Map<String, Object> param) {
		LOGGER.debug("update sql: {}; param:{}", sql, param);

		int ret = namedParameterJdbcTemplate.update(sql, param);

		LOGGER.debug("rows affected: {}", ret);
		return ret;
	}

	@Transactional
	public int[] batchUpdate(String sql, List<? extends Map<String, Object>> params) {
		LOGGER.debug("update sql: {}; params:{}", sql, params);

		@SuppressWarnings("unchecked")
		int[] ret = namedParameterJdbcTemplate.batchUpdate(sql, params.toArray(new Map[0]));

		LOGGER.debug("rows affected[]:{}", Arrays.toString(ret));
		return ret;
	}

	public long count(String sql, Map<String, Object> param) {
		LOGGER.debug("count sql: {}; param:{}", sql, param);

		Long count = namedParameterJdbcTemplate.queryForObject(sql, param,
				SingleColumnRowMapper.newInstance(Long.class));

		LOGGER.debug("count:{}", count);
		return count;
	}

	public <T> List<T> queryLang(String sql, Map<String, Object> param, Class<T> retClass) {
		LOGGER.debug("query sql: {}; param:{}", sql, param);

		List<T> results = namedParameterJdbcTemplate.query(sql, param, SingleColumnRowMapper.newInstance(retClass));

		LOGGER.debug("results size:{}", results.size());
		return results;
	}
	
	public <T> T queryLangFirst(String sql, Map<String, Object> param, Class<T> retClass) {
		LOGGER.debug("query sql: {}; param:{}", sql, param);
		
		List<T> results = namedParameterJdbcTemplate.query(sql, param, SingleColumnRowMapper.newInstance(retClass));
		
		LOGGER.debug("results size:{}", results.size());
		if (results.isEmpty()) {
			return null;
		}
		T first = results.get(0);
		LOGGER.debug("first ret:{}", first);
		return first;
	}

	public <T> List<T> query(String sql, Map<String, Object> param, Class<T> retClass) {
		LOGGER.debug("query sql: {}; param:{}", sql, param);

		List<T> results = namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(retClass));

		LOGGER.debug("results size:{}", results.size());
		return results;
	}

	public <T> T queryFirst(String sql, Map<String, Object> param, Class<T> retClass) {
		LOGGER.debug("query sql: {}; param:{}", sql, param);

		List<T> results = namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(retClass));

		LOGGER.debug("results size:{}", results.size());
		if (results.isEmpty()) {
			return null;
		}
		T first = results.get(0);
		LOGGER.debug("first ret:{}", first);
		return first;
	}

	public List<Map<String, Object>> queryMap(String sql, Map<String, Object> param) {
		LOGGER.debug("sql:{} ; param:{}", sql, param);

		List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(sql, param);

		LOGGER.debug("results size:{}", results.size());
		return results;
	}
	
	public <K, V> Map<K, V> queryToMap(String sql, Map<String, Object> param, String keyName, Class<K> keyType, String valName, Class<V> valType) {
		LOGGER.debug("sql:{} ; param:{}", sql, param);
		
		List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(sql, param);
		
		LOGGER.debug("results size:{}", results.size());
		
		Map<K, V> map = new LinkedHashMap<>();
		for(Map<String, Object> obj : results){
			map.put(keyType.cast(obj.get(keyName)), valType.cast(obj.get(valName))); 
		}
		
		return map;
	}
}
