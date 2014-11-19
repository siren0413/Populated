package com.siren.engine.service;

import com.siren.engine.SystemContext;
import com.siren.engine.app.EngineApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by yijunmao on 11/11/14.
 */
public class ClassRegisterService {

    static Logger LOGGER = LoggerFactory.getLogger(ClassRegisterService.class);

    public static void registerSingleton(Class clazz) throws IllegalAccessException, InstantiationException {
        if(clazz == null) return;
        LOGGER.info("Register singleton services for " + clazz.getName());
        Object obj = clazz.newInstance();
        SystemContext.APP_REGISTRY.addSingletonResource(obj);
    }

    public static void registerPerAppResource(Class clazz){
        if(clazz == null) return;
        LOGGER.info("Register per-application services for " + clazz.getName());
        SystemContext.APP_REGISTRY.addPerRequestResource(clazz);
    }
}
