package com.siren.engine.utils;

import java.io.File;

/**
 * Created by yijunmao on 11/11/14.
 */
public class ClasspathUtils {

    public static String buildClassPath(String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            if (path.endsWith("*")) {
                path = path.substring(0, path.length() - 1);
                File pathFile = new File(path);
                for (File file : pathFile.listFiles()) {
                    if (file.isFile() && file.getName().endsWith(".jar")) {
                        sb.append(path);
                        sb.append(file.getName());
                        sb.append(System.getProperty("path.separator"));
                    }
//                    else if(file.isFile() && file.getName().endsWith(".class")){
//                        sb.append(path);
//                        sb.append(file.getName());
//                        sb.append(System.getProperty("path.separator"));
//                    }
                }
            } else {
                sb.append(path);
                sb.append(System.getProperty("path.separator"));
            }
        }
        return sb.toString();
    }
}
