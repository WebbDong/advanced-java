package com.webbdong.classloader.loadxar;

import com.webbdong.classloader.util.ByteCodeUtils;
import com.webbdong.classloader.util.ZipUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * 加载 xar 类加载器
 * @author Webb Dong
 * @date 2021-02-27 10:42 AM
 */
public class XarClassLoader extends ClassLoader {

    private String xar;

    public XarClassLoader(String xar) {
        this.xar = xar;
    }

    @SneakyThrows
    @Override
    public Class<?> findClass(String name) {
        URL resourceUrl = XarClassLoader.class.getClassLoader().getResource(xar);
        if (resourceUrl == null) {
            throw new FileNotFoundException(xar);
        }
        String xlassName = name.endsWith(".xlass") ? name.replace(".xlass", "") : name;
        byte[] bytes = ZipUtils.unzipFileToBytes(new File(resourceUrl.toURI()), name);
        if (bytes == null) {
            throw new ClassNotFoundException(name);
        }
        ByteCodeUtils.xlassDecode(bytes);
        return defineClass(xlassName, bytes, 0, bytes.length);
    }

}
