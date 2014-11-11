package com.siren.engine.app;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yijunmao on 11/11/14.
 */
public class EngineApp extends Application{
    private Set<Object> singletons = new HashSet<>();
    private java.util.Set<Class<?>> classes = new HashSet<>();

    public EngineApp() {

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
