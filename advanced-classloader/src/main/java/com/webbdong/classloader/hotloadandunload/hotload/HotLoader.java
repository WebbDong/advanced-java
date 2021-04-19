package com.webbdong.classloader.hotloadandunload.hotload;

import com.webbdong.classloader.util.ByteCodeUtils;
import lombok.SneakyThrows;

import java.io.FileInputStream;

/**
 * 类热加载器
 * @author Webb Dong
 * @date 2021-04-18 6:05 PM
 */
public class HotLoader {

    private HotLoadClassLoader classLoader;

    public HotLoader(String basePath) {
        classLoader = new HotLoadClassLoader(basePath);
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return classLoader.findClass(className);
    }

    /**
     * 热加载类加载器
     * @author Webb Dong
     * @date 2021-04-18 4:29 PM
     */
    public static class HotLoadClassLoader extends ClassLoader {

        /**
         * 要加载的 class 类的根目录
         */
        private String basePath;

        public HotLoadClassLoader(String basePath) {
            this.basePath = basePath;
        }

        @SneakyThrows
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            String classPath = new StringBuilder()
                    .append(basePath)
                    .append(name.replaceAll("\\.", "/"))
                    .append(".class")
                    .toString();
            byte[] bytes = ByteCodeUtils.readLocalClassAsBytes(new FileInputStream(classPath));
            return defineClass(name, bytes, 0, bytes.length);
        }

    }

}
