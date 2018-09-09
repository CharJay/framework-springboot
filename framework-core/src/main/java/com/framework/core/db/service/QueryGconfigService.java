package com.framework.core.db.service;

import javax.annotation.Resource;

import com.framework.core.constants.CoreCacheConstants;
import com.framework.core.db.bean.GconfigQo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.framework.core.db.dao.QueryGconfigDao;

@Service
public class QueryGconfigService {

	@Resource
	private QueryGconfigDao queryGconfigDao;

	
	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.string'")
	public String getString(Class<?> clazz, String groupName, String configName) {
		return queryGconfigDao.getValue(clazz, new GconfigQo(groupName, configName));
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.integer'")
	public Integer getInteger(Class<?> clazz, String groupName, String configName) {
		String val = getString(clazz, groupName, configName);
		if (val != null)
			return Integer.valueOf(val);
		else
			return null;
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.long'")
	public Long getLong(Class<?> clazz, String groupName, String configName) {
		String val = getString(clazz, groupName, configName);
		if (val != null)
			return Long.valueOf(val);
		else
			return null;
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.double'")
	public Double getDouble(Class<?> clazz, String groupName, String configName) {
		String val = getString(clazz, groupName, configName);
		if (val != null)
			return Double.valueOf(val);
		else
			return null;
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.bool'")
	public Boolean getBool(Class<?> clazz, String groupName, String configName) {
		String val = getString(clazz, groupName, configName);
		if (val != null)
			return Boolean.valueOf(val);
		else
			return null;
	}
	
	
	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.string'")
	public String getString(String configTableName, String groupName, String configName) {
		return queryGconfigDao.getValue(configTableName, new GconfigQo(groupName, configName));
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.integer'")
	public Integer getInteger(String configTableName, String groupName, String configName) {
		String val = getString(configTableName, groupName, configName);
		if (val != null)
			return Integer.valueOf(val);
		else
			return null;
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.long'")
	public Long getLong(String configTableName, String groupName, String configName) {
		String val = getString(configTableName, groupName, configName);
		if (val != null)
			return Long.valueOf(val);
		else
			return null;
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.double'")
	public Double getDouble(String configTableName, String groupName, String configName) {
		String val = getString(configTableName, groupName, configName);
		if (val != null)
			return Double.valueOf(val);
		else
			return null;
	}

	@Cacheable(cacheNames = CoreCacheConstants.CACHE_5MIN, key = "#p0 + '.' + #p1 + '.' + #p2 + '.bool'")
	public Boolean getBool(String configTableName, String groupName, String configName) {
		String val = getString(configTableName, groupName, configName);
		if (val != null)
			return Boolean.valueOf(val);
		else
			return null;
	}

}
