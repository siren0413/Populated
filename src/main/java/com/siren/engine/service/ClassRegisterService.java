package com.siren.engine.service;

import com.siren.engine.app.EngineApp;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by yijunmao on 11/11/14.
 */
public class ClassRegisterService {
    public void register(Object app, String classPath, boolean singleton) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Class clazz = EngineApp.class;
        Field field = null;
        try{
            if(singleton){
                field = clazz.getDeclaredField("singletons");
                field.setAccessible(true);
                Set set = (Set)field.get(app);
                Class c = Class.forName(classPath);
                set.add(c.newInstance());
            }else{
                field = clazz.getDeclaredField("classes");
                field.setAccessible(true);
                Set set = (Set)field.get(app);
                Class c = Class.forName(classPath);
                set.add(c);
            }
        }finally {
            if(field!=null){
                field.setAccessible(false);
            }
        }
    }
}
