package com.siren.engine.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.siren.engine.model.TemplateData;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yijunmao on 11/11/14.
 */
public class TemplateCompileService {

    Handlebars handlebars = null;
    String target;

    public TemplateCompileService(String target){
        handlebars = new Handlebars();
        this.target = target;
    }

    public String compile(Object data) throws IOException {
        Template template = handlebars.compile(target);
        String output = template.apply(data);
        return output;
    }
}
