package com.webbdong.classloader.util;

import java.io.File;

/**
 * @author Webb Dong
 * @date 2021-04-18 11:05 PM
 */
public class FileUtils {

    private FileUtils() {}

    public static long getLastModifiedTime(String path) {
        if (path == null || "".equals(path)) {
            return -1;
        }
        File file = new File(path);
        return getLastModifiedTime(file);
    }

    public static long getLastModifiedTime(File file) {
        if (!file.exists() || file.isDirectory()) {
            return -1;
        }
        return file.lastModified();
    }

}
