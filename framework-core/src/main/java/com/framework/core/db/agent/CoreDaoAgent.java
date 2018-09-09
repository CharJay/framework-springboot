package com.framework.core.db.agent;

import javax.annotation.Resource;

import com.framework.core.db.service.QueryGconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.core.db.dao.QuickCrudDao;
import com.framework.core.db.dao.QuickSimpleDao;
import com.framework.core.db.dao.RedisListSimpleDao;
import com.framework.core.db.dao.RedisMapSimpleDao;
import com.framework.core.db.dao.RedisSimpleDao;

@Component
public class CoreDaoAgent {

    @Resource
    public QuickSimpleDao      quickSimpleDao;
    @Resource
    public QuickCrudDao        quickCrudDao;
    @Resource
    public RedisSimpleDao      redisSimpleDao;
    @Resource
    public RedisMapSimpleDao   redisMapSimpleDao;
    @Resource
    public RedisListSimpleDao  redisListSimpleDao;
    @Autowired
    public QueryGconfigService gconfig;

}
