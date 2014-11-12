package com.siren.engine.app;

import com.siren.engine.service.ClassCompileService;
import com.siren.engine.service.ClassRegisterService;
import com.siren.engine.service.TemplateCompileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by yijunmao on 11/11/14.
 */
public class AppGenerator {

    static Logger LOGGER = LoggerFactory.getLogger(AppGenerator.class);

    TemplateCompileService templateCompileService;
    ClassCompileService classCompileService;
    ClassRegisterService classRegisterService;

    public AppGenerator(){
        templateCompileService = new TemplateCompileService();
        classCompileService = new ClassCompileService();
        classRegisterService = new ClassRegisterService();
    }

    public String generate(Object container, Object data, String className, String template, boolean singleton, boolean register){
        try {
            String src = templateCompileService.compile(data, template);
            String cp = classCompileService.compile(src, className);
            if(register)
                classRegisterService.register(container, className, singleton);
            return cp;
        } catch (IOException e) {
            LOGGER.error("File not found",e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found", e);
        } catch (InstantiationException e) {
            LOGGER.error("Java reflection error", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Java reflection illegal access", e);
        } catch (NoSuchFieldException e) {
            LOGGER.error("Java reflection no such field",e);
        }
        return null;
    }
}
