package com.siren.engine.app;

import com.siren.engine.SystemContext;

import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yijunmao on 11/11/14.
 */
public class EngineApp extends Application{
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public EngineApp() {
        // export app
        SystemContext.app = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<>();
                map.put("className", "HelloWorld");
                map.put("supportMediaType","MediaType.APPLICATION_JSON");
                map.put("model","TestModel");
                Map<String, String> modelMap = new HashMap<>();
                modelMap.put("className","TestModel");
                AppGenerator generator = new AppGenerator();
                generator.generate(SystemContext.app, modelMap, "TestModel", SystemContext.MODEL_TEMPLATE_FILE, false, false);
                generator.generate(SystemContext.app, map, "HelloWorld", SystemContext.APP_TEMPLATE_FILE, false, true);
            }
        }).start();
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
