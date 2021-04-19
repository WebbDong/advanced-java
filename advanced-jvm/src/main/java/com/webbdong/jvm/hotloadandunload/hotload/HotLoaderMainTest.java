package com.webbdong.jvm.hotloadandunload.hotload;

/**
 * 类热加载
 * @author Webb Dong
 * @date 2021-04-19 12:04 AM
 */
public class HotLoaderMainTest {

    public static void main(String[] args) {
        final String basePath = "D:/develop/workspace/java/advanced-java/advanced-jvm/target/classes/";
        final String className = "com.webbdong.jvm.hotloadandunload.TestClass";
        ClassFileUpdatedObserver observer = new ClassFileUpdatedObserver(basePath, className);
        observer.addObserver((o, arg) -> {
            try {
                // 同一个类加载器无法替换已加载过的类，所以必须每次创建新的实例
                // 强转会发生类型转换错误
//                TestClass testClass = (TestClass) new HotLoader(basePath).loadClass(className).newInstance();
                Class<?> aClass = new HotLoader(basePath).loadClass(className);
                Object obj = aClass.newInstance();
                aClass.getMethod("run").invoke(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        observer.startObserver();
    }

}
