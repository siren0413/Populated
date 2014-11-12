package com.siren.engine.service;

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

    public void register(Object app, String fullClassName, boolean singleton) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Class clazz = EngineApp.class;
        Field field = null;

        try{
            if(singleton){
                LOGGER.info("Register singleton services for " + fullClassName);
                field = clazz.getDeclaredField("singletons");
                field.setAccessible(true);
                Set set = (Set)field.get(app);
                Class c = Class.forName(fullClassName);
                set.add(c.newInstance());
            }else{
                LOGGER.info("Register per-application services for " + fullClassName);
                field = clazz.getDeclaredField("classes");
                field.setAccessible(true);
                Set set = (Set)field.get(app);
                Class c = Class.forName(fullClassName);
                set.add(c);
            }
            LOGGER.info("[SUCCESS] register for " + fullClassName);
        }finally {
            if(field!=null){
                field.setAccessible(false);
            }
        }
    }
}
