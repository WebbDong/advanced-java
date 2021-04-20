package com.webbdong.classloader.hotloadandunload.hotload;

import com.webbdong.classloader.hotloadandunload.TestClass;
import com.webbdong.classloader.util.ByteCodeUtils;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 通过 ByteBuddy 实现类热加载
 * @author Webb Dong
 * @date 2021-04-20 1:56 AM
 */
public class HotLoaderByByteBuddy {

    @SneakyThrows
    public static Class<?> replaceClass(String basePath, String className) {
        String classPath = new StringBuilder()
                .append(basePath)
                .append(className.replaceAll("\\.", "/"))
                .append(".class")
                .toString();
        try (InputStream in = new FileInputStream(classPath)) {
            if (in == null) {
                throw new ClassNotFoundException("class not found " + className);
            }

            byte[] bytes = ByteCodeUtils.readLocalClassAsBytes(in);
            return new ByteBuddy()
                    .redefine(TestClass.class, ClassFileLocator.Simple.of(className, bytes))
                    .make()
                    .load(HotLoaderByByteBuddy.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
                    .getLoaded();
        }
    }

}
