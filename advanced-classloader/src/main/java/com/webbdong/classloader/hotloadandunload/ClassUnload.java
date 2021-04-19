package com.webbdong.classloader.hotloadandunload;

import com.webbdong.classloader.util.ByteCodeUtils;
import lombok.SneakyThrows;

import java.io.FileInputStream;

/**
 * 类卸载
 *  由 JVM 自带的类加载器所加载的类，在 JVM 的生命周期中，始终不会被卸载。
 *  JVM 本身会始终引用这些类加载器，而这些类加载器则会始终引用它们所加载的类的 Class 对象
 *  自定义的类加载器加载的类是可以被卸载的。
 * @author Webb Dong
 * @date 2021-04-19 11:45 AM
 */
public class ClassUnload {

    /*
        -verbose:class : 同时追踪类的加载和卸载
        -XX:+TraceClassLoading : 单独跟踪类的加载
        -XX:+TranceUnloading : 单独跟踪类的卸载

        类的卸载必须满足的条件:
            1、必须是由自定义类加载器加载
            2、该类的所有实例对象不可达
            3、该类的 Class 对象不可达
            4、该类的 ClassLoader 对象不可达
     */
    @SneakyThrows
    public static void main(String[] args) {
        final String basePath = "D:/develop/workspace/java/advanced-java/advanced-jvm/target/classes/";
        final String className = "com.webbdong.jvm.hotloadandunload.TestClass";
        MyClassLoader classLoader = new MyClassLoader(basePath);
        Class<?> aClass = classLoader.findClass(className);
        Object obj = aClass.newInstance();
        aClass.getMethod("run").invoke(obj);

        aClass = null;
        obj = null;
        classLoader = null;
        System.gc();
    }

    private static class MyClassLoader extends ClassLoader {

        private String basePath;

        public MyClassLoader(String basePath) {
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
