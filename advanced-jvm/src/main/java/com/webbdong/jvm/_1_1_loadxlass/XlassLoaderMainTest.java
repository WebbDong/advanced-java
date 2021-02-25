package com.webbdong.jvm._1_1_loadxlass;

/**
 * 1.1 使用自定义Classloader机制，实现xlass的加载
 * @author Webb Dong
 * @date 2021-02-25 9:23 PM
 */
public class XlassLoaderMainTest {

    public static void main(String[] args) throws Exception {
        XlassLoader xlassLoader = new XlassLoader();
        Class<?> aClass1 = xlassLoader.loadClass("Hello.xlass");
        Object obj1 = aClass1.newInstance();
        aClass1.getDeclaredMethod("hello").invoke(obj1);

        Class<?> aClass2 = Class.forName("Hello", false, xlassLoader);
        Object obj2 = aClass2.newInstance();
        aClass2.getDeclaredMethod("hello").invoke(obj2);
    }

}
