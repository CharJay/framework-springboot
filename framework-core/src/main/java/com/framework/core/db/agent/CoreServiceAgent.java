package com.framework.core.db.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.core.db.service.QueryGconfigService;
import com.framework.core.db.service.QuickCrudService;
import com.framework.core.db.service.QuickSimpleService;
import com.framework.core.db.service.RedisHashSimpleService;
import com.framework.core.db.service.RedisListSimpleService;
import com.framework.core.db.service.RedisSetSimpleService;
import com.framework.core.db.service.RedisSimpleService;

@Component
public class CoreServiceAgent {

    @Autowired
    public RedisSimpleService redisSimpleService;
    @Autowired
    public RedisHashSimpleService redisHashSimpleService;
    @Autowired
    public RedisListSimpleService redisListSimpleService;
    @Autowired
    public RedisSetSimpleService redisSetSimpleService;
    @Autowired
    public QuickSimpleService quickSimpleService;
    @Autowired
    public QuickCrudService       quickCrudService;
    @Autowired
    public QueryGconfigService    gconfig;

}
