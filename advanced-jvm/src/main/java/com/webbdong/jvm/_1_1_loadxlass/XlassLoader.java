package com.webbdong.jvm._1_1_loadxlass;

import com.webbdong.jvm.util.ByteCodeUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 加载 xlass 类加载器
 * @author Webb Dong
 * @date 2021-02-25 9:23 PM
 */
public class XlassLoader extends ClassLoader {

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String xlassName = name.endsWith(".xlass") ? name.replace(".xlass", "") : name;
        String xlassFileName = name.contains(".xlass") ? name : name + ".xlass";
        byte[] bytes;
        try (InputStream in = XlassLoader.class.getClassLoader().getResourceAsStream(xlassFileName)) {
            if (in == null) {
                throw new ClassNotFoundException(name);
            }
            bytes = ByteCodeUtils.readLocalClassAsBytes(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteCodeUtils.xlassDecode(bytes);
        return defineClass(xlassName, bytes, 0, bytes.length);
    }

}
