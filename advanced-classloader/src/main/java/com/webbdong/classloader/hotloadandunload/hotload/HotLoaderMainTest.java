package com.webbdong.classloader.hotloadandunload.hotload;

import com.webbdong.classloader.hotloadandunload.TestClass;

/**
 * 类热加载（动态加载）
 * @author Webb Dong
 * @date 2021-04-19 12:04 AM
 */
public class HotLoaderMainTest {

    /**
     * 通过创建新的类加载器实现热加载
     */
    private static void hotLoadByClassLoader() {
        final String basePath = "D:/develop/workspace/java/advanced-java/advanced-classloader/target/classes/";
        final String className = "com.webbdong.classloader.hotloadandunload.TestClass";
        ClassFileUpdatedObserver observer = new ClassFileUpdatedObserver(basePath, className);
        observer.addObserver((o, arg) -> {
            try {
                // 同一个类加载器无法替换已加载过的类，所以必须每次创建新的实例
                // 强转会发生类型转换错误
//                TestClass testClass = (TestClass) new HotLoader(basePath).loadClass(className).newInstance();
                Class<?> aClass = new HotLoaderByClassLoader(basePath).loadClass(className);
                Object obj = aClass.newInstance();
                aClass.getMethod("run").invoke(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        observer.startObserver();
    }

    /**
     * 通过 ByteBuddy 替换类字节码实现热加载
     * -javaagent:D:/develop/tools/apache-maven-3.6.3-bin/repository/net/bytebuddy/byte-buddy-agent/1.10.20/byte-buddy-agent-1.10.20.jar
     */
    private static void hotLoadByByteBuddy() {
        final String basePath = "D:/develop/workspace/java/advanced-java/advanced-classloader/target/classes/";
        final String className = "com.webbdong.classloader.hotloadandunload.TestClass";
        ClassFileUpdatedObserver observer = new ClassFileUpdatedObserver(basePath, className);
        observer.addObserver((o, arg) -> {
            try {
                Class<?> aClass = HotLoaderByByteBuddy.replaceClass(basePath, className);
                TestClass testClass = (TestClass) aClass.newInstance();
                testClass.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        observer.startObserver();
    }

    public static void main(String[] args) {
//        hotLoadByClassLoader();
        hotLoadByByteBuddy();
    }

}
