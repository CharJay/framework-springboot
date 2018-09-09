package com.framework.core.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.framework.core.db.bean.GconfigQo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.framework.core.db.bean.BeanCamelHelper;

@Repository
public class QueryGconfigDao {

    private static final Logger        LOGGER = LoggerFactory.getLogger(QueryGconfigDao.class);

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public <T> String getValue(Class<T> clazz, GconfigQo qo) {
        BeanCamelHelper bc = new BeanCamelHelper(clazz);

        Map<String, Object> param = new HashMap<>();
        String sql = " select config_val from " + bc.getTableName() + " where is_del=0 "
                + (qo.getGroupName() == null ? "" : " and group_name=:groupName")
                + (qo.getConfigName() == null ? "" : " and config_name=:configName")
                + (qo.getPortalKey() == null ? "" : " and portal_key=:portalKey");
        if (qo.getConfigName() != null) {
            param.put("configName", qo.getConfigName());
        }
        if (qo.getGroupName() != null) {
            param.put("groupName", qo.getGroupName());
        }
        if (qo.getPortalKey() != null) {
            param.put("portalKey", qo.getPortalKey());
        }
        LOGGER.debug("sql:{} ; param:{}; clazz:{}; ", sql, param, clazz);
//        String str = namedParameterJdbcTemplate.queryForObject(sql, param, String.class);
        List<String> list = namedParameterJdbcTemplate.queryForList(sql, param, String.class);
        if (list.size()==0) {
            return null;
        }
        String str = list.get(0);
        return str;
    }
    
    public String getValue(String configTableName, GconfigQo qo) {
    	Map<String, Object> param = new HashMap<>();
    	String sql = " select config_val from " + configTableName + " where is_del=0 "
    			+ (qo.getGroupName() == null ? "" : " and group_name=:groupName")
    			+ (qo.getConfigName() == null ? "" : " and config_name=:configName")
    			+ (qo.getPortalKey() == null ? "" : " and portal_key=:portalKey");
    	if (qo.getConfigName() != null) {
    		param.put("configName", qo.getConfigName());
    	}
    	if (qo.getGroupName() != null) {
    		param.put("groupName", qo.getGroupName());
    	}
    	if (qo.getPortalKey() != null) {
    		param.put("portalKey", qo.getPortalKey());
    	}
    	LOGGER.debug("sql:{} ; param:{}; table:{}; ", sql, param, configTableName);
//        String str = namedParameterJdbcTemplate.queryForObject(sql, param, String.class);
    	List<String> list = namedParameterJdbcTemplate.queryForList(sql, param, String.class);
    	if (list.size()==0) {
    		return null;
    	}
    	String str = list.get(0);
    	return str;
    }
}
