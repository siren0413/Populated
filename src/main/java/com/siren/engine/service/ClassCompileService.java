package com.siren.engine.service;

import com.siren.engine.utils.ClasspathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yijunmao on 11/11/14.
 */
public class ClassCompileService {

    static Logger LOGGER = LoggerFactory.getLogger(ClassCompileService.class);

    public String compile(String src, String className, String... classpath) {

        // setup compiler
        LOGGER.debug("Start compilation for class: "+className);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        JavaFileObject file = new JavaSourceFromString(className , src);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);

        // setup output dir and classpath
        String outputPath = ClassCompileService.class.getClassLoader().getResource("").getPath();
        LOGGER.debug("Set output dir for class: "+ outputPath);
        String cp = outputPath.substring(0, outputPath.indexOf("classes")) + "lib/*";
        List<String> optionList = new ArrayList<>();
        optionList.addAll(Arrays.asList("-d", outputPath));
        optionList.addAll(Arrays.asList("-cp", ClasspathUtils.buildClassPath(cp, outputPath)));
        LOGGER.debug("Set -classpath for class: "+ outputPath);
        LOGGER.debug("Set -classpath for class: "+ cp);

        // prepare compile
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, optionList, null, compilationUnits);
        LOGGER.trace(src);

        // compile
        boolean success = task.call();

        // handle result
        if(!success) {
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
//                System.out.println(diagnostic.getCode());
//                System.out.println(diagnostic.getKind());
//                System.out.println(diagnostic.getPosition());
//                System.out.println(diagnostic.getStartPosition());
//                System.out.println(diagnostic.getEndPosition());
//                System.out.println(diagnostic.getSource());
                LOGGER.error(diagnostic.getMessage(null));
            }
        }
        LOGGER.info("[SUCCESS] Compilation {class: " + className +", path: "+outputPath + className +".class"+"}");

        if(success)
            return outputPath + className +".class";
        return null;
    }

    class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}
