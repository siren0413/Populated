package com.siren.engine.service;

import javax.tools.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yijunmao on 11/11/14.
 */
public class ClassCompileService {

    public String compile(String src, String className) {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        JavaFileObject file = new JavaSourceFromString(className , src);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);

        String classPath = ClassCompileService.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        List<String> optionList = new ArrayList<>();
        optionList.addAll(Arrays.asList("-d", classPath));

        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, optionList, null, compilationUnits);

        boolean success = task.call();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.println(diagnostic.getCode());
            System.out.println(diagnostic.getKind());
            System.out.println(diagnostic.getPosition());
            System.out.println(diagnostic.getStartPosition());
            System.out.println(diagnostic.getEndPosition());
            System.out.println(diagnostic.getSource());
            System.out.println(diagnostic.getMessage(null));

        }
        System.out.println("Success: " + success);

        if(success)
            return classPath;
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
