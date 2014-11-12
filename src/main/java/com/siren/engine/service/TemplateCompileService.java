package com.siren.engine.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.siren.engine.model.TemplateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yijunmao on 11/11/14.
 */
public class TemplateCompileService {

    static Logger LOGGER = LoggerFactory.getLogger(ClassRegisterService.class);

    Handlebars handlebars = null;

    public TemplateCompileService(){
        handlebars = new Handlebars();
    }

    public String compile(Object data, String template) throws IOException {
        LOGGER.info("Start template compilation for template: " + template);
        Template temp = handlebars.compile(template);
        String output = temp.apply(data);
        LOGGER.info("[SUCCESS] template compilation: "+ template);
        return output;
    }
}
