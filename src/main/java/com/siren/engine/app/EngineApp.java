package com.siren.engine.app;

import com.siren.engine.SystemContext;
import com.siren.engine.service.ClassCompileService;
import com.siren.engine.service.ClassRegisterService;
import com.siren.engine.service.TemplateCompileService;
import org.jboss.resteasy.core.Dispatcher;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yijunmao on 11/11/14.
 */
@ApplicationPath("/")
public class EngineApp extends Application{

    public EngineApp(@Context final Dispatcher dispatcher) {

        // export app registry for register resources
        SystemContext.APP_REGISTRY = dispatcher.getRegistry();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Map<String, String> map = new HashMap<>();
                map.put("className", "HelloWorld");
                map.put("supportMediaType","MediaType.APPLICATION_JSON");
                map.put("model","TestModel");
                map.put("appPath","hello");
                Map<String, String> modelMap = new HashMap<>();
                modelMap.put("className","TestModel");

                try {
                    // model
                    String model_src = TemplateCompileService.compile(modelMap, SystemContext.MODEL_TEMPLATE_FILE);
                    Class model_clazz = ClassCompileService.compile(model_src, modelMap.get("className"));

                    //app
                    String app_src = TemplateCompileService.compile(map, SystemContext.APP_TEMPLATE_FILE);
                    Class app_clazz = ClassCompileService.compile(app_src, map.get("className"));
                    ClassRegisterService.registerPerAppResource(app_clazz);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
