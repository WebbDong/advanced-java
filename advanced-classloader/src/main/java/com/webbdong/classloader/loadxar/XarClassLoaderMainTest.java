package com.webbdong.classloader.loadxar;

import lombok.SneakyThrows;

/**
 * 1.2 实现xlass打包的xar（类似class文件打包的jar）的加载
 * @author Webb Dong
 * @date 2021-02-27 12:41 PM
 */
public class XarClassLoaderMainTest {

    @SneakyThrows
    public static void main(String[] args) {
        XarClassLoader xarClassLoader = new XarClassLoader("Hello.xar");
        Class<?> xlass = xarClassLoader.loadClass("Hello.xlass");
        Object obj = xlass.newInstance();
        xlass.getDeclaredMethod("hello").invoke(obj);
    }

}
