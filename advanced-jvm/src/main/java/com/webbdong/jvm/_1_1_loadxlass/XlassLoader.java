package com.webbdong.jvm._1_1_loadxlass;

import com.webbdong.jvm.util.ByteCodeUtils;

/**
 * 加载 xlass
 * @author Webb Dong
 * @date 2021-02-25 9:23 PM
 */
public class XlassLoader extends ClassLoader {

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String xlassName = name.endsWith(".xlass") ? name.replace(".xlass", "") : name;
        String xlassFileName = name.contains(".xlass") ? name : name + ".xlass";
        byte[] bytes = ByteCodeUtils.readLocalClassAsBytes(
                XlassLoader.class.getClassLoader().getResourceAsStream(xlassFileName));
        ByteCodeUtils.xlassDecode(bytes);
        return defineClass(xlassName, bytes, 0, bytes.length);
    }

}
