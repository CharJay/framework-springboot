package com.framework.core.db.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.framework.core.db.bean.ListDataWrap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.framework.core.db.bean.BeanCamelHelper;
import com.framework.core.db.bean.Page;


/**
 * 此类用于常规crud，由于用到大量反射，执行效率低下，
 * 需要高效率执行sql，请使用QuickSimpleDao
 * @author
 *
 */
@Repository
public class QuickCrudDao {
    
    private static final Logger        LOGGER = LoggerFactory.getLogger( QuickCrudDao.class );
    
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    /**
     * 插入一个对象，并返回这个对象的自增id
     * @param obj: pojo对象
     * @return
     */
    public Number create( Object pojo ) {
        KeyHolder holder = new GeneratedKeyHolder();
        
        BeanCamelHelper bc = new BeanCamelHelper( pojo );
        
        String colstr=bc.getNotNullunderscoreStr( "`", "`", "`,`" );
        String valstr=bc.getNotNullCamelCaseStr( ":", "", ",:" );
        if(bc.isAllProNull()){
            throw new RuntimeException("传入参数属性均为null");
        }
        String tableName = bc.getTableName();
        
        String sql="insert into "+tableName+"("+colstr+") values("+valstr+")";
        LOGGER.debug( "sql:{}; param: {}", sql, pojo );
        
        int ret = namedParameterJdbcTemplate.update(sql,
                new BeanPropertySqlParameterSource(pojo), holder);
        
        LOGGER.debug( "ret:{}, keyid:{}", ret , holder.getKey());
        return holder.getKey();
    }
    
    public int[] createBatch( List<? extends Object> pojos ) {
    	if(pojos == null || pojos.size() == 0) {
    		return new int[]{};
    	}
        KeyHolder holder = new GeneratedKeyHolder();
        
        SqlParameterSource[] batchArgs = new SqlParameterSource[pojos.size()];
        for (int i = 0; i < pojos.size(); i++) {
            Object pojo = pojos.get( i );
            batchArgs[i] = new BeanPropertySqlParameterSource( pojo );
        }
        BeanCamelHelper bc = new BeanCamelHelper( pojos.get(0) );
        
        String colstr=bc.getNotNullunderscoreStr( "`", "`", "`,`" );
        String valstr=bc.getNotNullCamelCaseStr( ":", "", ",:" );
        if(bc.isAllProNull()){
            throw new RuntimeException("传入参数属性均为null");
        }
        String tableName = bc.getTableName();
        
        String sql="insert into "+tableName+"("+colstr+") values("+valstr+")";
        LOGGER.debug( "sql:{}; param: {}", sql, pojos );
        
        int[] ret = namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
        LOGGER.debug( "ret:{}, keyid:{}", ret , holder.getKey());
        return ret;
    }
    /**
     * 通过id更新对象
     * @param pojo对象
     * @return
     */
    public int updateById(Object pojo){
        
        BeanCamelHelper bc = new BeanCamelHelper( pojo );
        
        String together=bc.getNotNullTogetherStr( "", "", ",", "=:", false );
        if(bc.isAllProNull() || bc.getPkCamelName() == null || bc.getPkValue() == null){
            throw new RuntimeException("参数为空，主键为空 或者主键未设置@Id");
        }
        String tableName = bc.getTableName();
        
        String sql="update "+tableName+" set "+together+" where "+bc.getPkUnderscoreName()+" = :"+bc.getPkCamelName();
        LOGGER.debug( "sql:{}; param: {}", sql, pojo );
        
        int ret = namedParameterJdbcTemplate.update(sql,
                new BeanPropertySqlParameterSource(pojo));
        
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    /**
     * 通过条件更新对象
     * @param pojo对象
     * @param condition对象（也可以是pojo对象）
     * @return
     */
    public int updateByObj(Object pojo, Object condition){
        
        BeanCamelHelper bc = new BeanCamelHelper( pojo );
        BeanCamelHelper bcc = new BeanCamelHelper( condition );
        
        String together=bc.getNotNullTogetherStr( "", "", ",", "=:pojo", false );
        String cond=bcc.getNotNullTogetherStr( "", "", " and ", "=:cond", true );
        if( bc.isAllProNull() || bcc.isAllProNull() ){
            throw new RuntimeException("参数为空");
        }
        String tableName = bc.getTableName();
        
        Map<String,Object> param = new HashMap<>();
        bc.appendMapCamcelAndValuer( param, "pojo" );
        bcc.appendMapCamcelAndValuer( param, "cond" );
        
        String sql="update "+tableName+" set "+together+" where "+cond;
        if(LOGGER.isInfoEnabled())
        	LOGGER.debug( "sql:{}; param: {}", sql, StringUtils.left(param.toString(), 255));
        
        int ret = namedParameterJdbcTemplate.update(sql,param);
        
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    /**
     * 通过条件更新对象
     * @param pojo对象
     * @param whereStr :where条件，不需要传where前缀：（ xxx=:xxx or yyy=:yyy)，注意不要和pojo重名
     * @param condition对象，属性必须与whereStr参数名称一致
     * @return
     */
    public int updateByObj(Object pojo, String whereStr, Object condition){
        
        BeanCamelHelper bc = new BeanCamelHelper( pojo );
        BeanCamelHelper bcc = new BeanCamelHelper( condition );
        
        String together=bc.getNotNullTogetherStr( "", "", ",", "=:", false );
        if( bc.isAllProNull() || bcc.isAllProNull() ){
            throw new RuntimeException("参数为空");
        }
        String tableName = bc.getTableName();
        
        Map<String,Object> param = new HashMap<>();
        bc.appendMapCamcelAndValuer( param, "" );
        bcc.appendMapCamcelAndValuer( param, "" );
        
        String sql="update "+tableName+" set "+together+" where "+whereStr;
        LOGGER.debug( "sql:{}; param: {}, whereStr: {}", sql, param , whereStr);
        
        int ret = namedParameterJdbcTemplate.update(sql,param);
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    /**
     * 通过id获取对象
     * @param clazz
     * @param id
     * @return 如果获取不到，则为null，如果有多个，获取第一个，并且打印警告日志
     */
    public <T>T getById(Class<T> clazz, Serializable id) {
        
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        
        Map<String, Object> param = new HashMap<>();
        param.put( bc.getPkCamelName(), id );
        
        String sql = " select * from "+ bc.getTableName() + " where "+bc.getPkUnderscoreName()+" = :"+bc.getPkCamelName();
        
        LOGGER.debug( "sql:{} ; param:{}; clazz:{}; id:{}", sql, param, clazz, id );

        List<T> ret = namedParameterJdbcTemplate.query( sql, param, new BeanPropertyRowMapper(clazz));
        
        
        LOGGER.debug( "ret.size:{}:{}", ret.size(), ret );
        if(ret.size()!=1){
            LOGGER.error( "getById 返回数据>0:{}", ret.size() );
            LOGGER.error( "getById 返回数据>0 >> sql:{} ; param:{}; clazz:{}; id:{}", sql, param, clazz, id );
        }
        return ret.size()==0 ? null : ret.get( 0 );
    }
    /**
     * 获取数据量
     * @param clazz
     * @param condition对象
     * @return
     */
    public long count(Class clazz){
        
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        
        String sql = "select count(1) from "+bc.getTableName() ;
        LOGGER.debug( "sql:{} ", sql );
        Number number = namedParameterJdbcTemplate.queryForObject( sql, new HashMap<String,Object>(), Long.class );
        long ret = (number != null ? number.longValue() : 0);
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    /**
     * 获取数据量
     * @param clazz
     * @param condition对象
     * @return
     */
    public long count(Class clazz, Object condition){
        
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        BeanCamelHelper bcc = new BeanCamelHelper( condition );
        
        String cond = "";
        if(!bcc.isAllProNull()){
        	cond = " where " + bcc.getNotNullTogetherStr( "", "", " and ", "=:", true );
        }
        
        String sql = "select count(1) from "+bc.getTableName() + cond;
        LOGGER.debug( "sql:{} ; param:{}", sql, condition );
        Number number = namedParameterJdbcTemplate.queryForObject( sql, new BeanPropertySqlParameterSource(condition), Long.class );
        long ret = (number != null ? number.longValue() : 0);
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }
    /**
     * 获取数据条数
     * @param clazz
     * @param whereStr 除where。如： xxx=:xxx and yyy=:yyy 
     * @param condition对象，属性必须与whereStr参数名称一致
     * @return
     */
    public long count(Class clazz, String whereStr, Object condition){
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        BeanCamelHelper bcc = new BeanCamelHelper( condition );
        
//        if(bcc.isAllProNull()){
//            throw new RuntimeException("传入对象所有属性值为null");
//        }
        
        String sql = "select count(1) from "+bc.getTableName()+" where "+whereStr;
        LOGGER.debug( "sql:{} ; param:{}", sql, condition );
        Number number = namedParameterJdbcTemplate.queryForObject( sql, new BeanPropertySqlParameterSource(condition), Long.class );
        long ret = (number != null ? number.longValue() : 0);
        LOGGER.debug( "ret:{}", ret );
        return ret;
    }

    /**
     * 获取列表数据
     * @param clazz
     * @param condition
     * @return
     */
    public <T>List<T> getList(Class<T> clazz){
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        
        
        String sql = "select * from "+bc.getTableName() ;
        LOGGER.debug( "sql:{} ", sql );
        
        List<T> ret = namedParameterJdbcTemplate.query( sql, new HashMap<String,Object>(),
                new BeanPropertyRowMapper(clazz));
        
        LOGGER.debug( "ret.size:{}", ret.size() );
        return ret;
    }
    public <T>List<T> getList(Class<T> clazz, Object condition){
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        BeanCamelHelper bcc = new BeanCamelHelper( condition );
        
        String cond = "";
        if(!bcc.isAllProNull()){
        	cond = " where " + bcc.getNotNullTogetherStr( "", "", " and ", "=:", true );
        }
        
        String sql = "select * from "+bc.getTableName() + cond;
        LOGGER.debug( "sql:{} ; param:{}", sql, condition );
        
        List<T> ret = namedParameterJdbcTemplate.query( sql, new BeanPropertySqlParameterSource(condition),
                new BeanPropertyRowMapper(clazz));
        
        LOGGER.debug( "ret.size:{}", ret.size() );
        return ret;
    }
    public <T>List<T> getList(Class<T> clazz, String whereStr, Object condition){
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        BeanCamelHelper bcc = new BeanCamelHelper( condition );
        
//        if(bcc.isAllProNull()){
//            throw new RuntimeException("传入对象所有属性值为null");
//        }
        
        String sql = "select * from "+bc.getTableName()+" where "+whereStr;
        LOGGER.debug( "sql:{} ; param:{}", sql, condition );
        
        List<T> ret = namedParameterJdbcTemplate.query( sql, new BeanPropertySqlParameterSource(condition),
                new BeanPropertyRowMapper(clazz));
        
        LOGGER.debug( "ret.size:{}", ret.size() );
        return ret;
    }
    
    public <T>ListDataWrap<T> getList(Class<T> clazz, Page page){
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        

        long total = count( clazz );
        
        String sql = "select * from "+bc.getTableName()+" limit "+(page.getBeginIndex()-1)+", "+page.getPageSize();
        LOGGER.debug( "sql:{} ", sql );
        
        List<T> list = namedParameterJdbcTemplate.query( sql, new HashMap<String,Object>(),
                new BeanPropertyRowMapper(clazz));
        
        ListDataWrap<T> ret = new ListDataWrap<>();
        ret.setTotal( total );
        ret.setList( list );
        
        LOGGER.debug( "ret.size:{}, total:{}", list.size(), total );
        
        return ret;
    }
    public <T>ListDataWrap<T> getList(Class<T> clazz, Object condition, Page page){
        return getList(clazz, condition, page, null);
    }
    
    public <T>ListDataWrap<T> getList(Class<T> clazz, Object condition, Page page, String orderby){
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
        BeanCamelHelper bcc = new BeanCamelHelper( condition );
        
        String cond = "";
        if(!bcc.isAllProNull()){
        	cond = " where " + bcc.getNotNullTogetherStr( "", "", " and ", "=:", true );
        }
        
        String odstr = "";
        if(orderby!=null && !"".equals(orderby)) {
        	odstr=" order by " + orderby;
        }
        
        long total = count( clazz, condition );
        
        String sql = "select * from "+bc.getTableName()+cond +odstr +" limit "+(page.getBeginIndex()-1)+", "+page.getPageSize();
        LOGGER.debug( "sql:{} ; param:{}", sql, condition );
        
        List<T> list = namedParameterJdbcTemplate.query( sql, new BeanPropertySqlParameterSource(condition),
                new BeanPropertyRowMapper(clazz));
        
        ListDataWrap<T> ret = new ListDataWrap<>();
        ret.setTotal( total );
        ret.setList( list );
        
        LOGGER.debug( "ret.size:{}, total:{}", list.size(), total );
        
        return ret;
    }
    
    public <T>ListDataWrap<T> getList(Class<T> clazz, String whereStr, Object condition, Page page){
        return getList( clazz, whereStr, condition, page, null );
    }
    public <T>ListDataWrap<T> getList(Class<T> clazz, String whereStr, Object condition, Page page, String orderBy){
        BeanCamelHelper bc = new BeanCamelHelper( clazz );
//        BeanCamelHelper bcc = new BeanCamelHelper( condition );
//        
//        if(bcc.isAllProNull()){
//            throw new RuntimeException("传入对象所有属性值为null");
//        }
        long total = count( clazz, whereStr, condition );
        
        String orderStr = "";
        if(orderBy!=null){
        	orderStr += " order by " + orderBy + " ";
        }
        String sql = "select * from "+bc.getTableName()+" where "+whereStr + orderStr+" limit "+(page.getBeginIndex()-1)+", "+page.getPageSize();
        LOGGER.debug( "sql:{} ; param:{}", sql, condition );
        
        List<T> list = namedParameterJdbcTemplate.query( sql, new BeanPropertySqlParameterSource(condition),
                new BeanPropertyRowMapper(clazz));
        
        ListDataWrap<T> ret = new ListDataWrap<>();
        ret.setTotal( total );
        ret.setList( list );
        
        LOGGER.debug( "ret.size:{},total:{}", list.size(),total );
        return ret;
    }
    
}
