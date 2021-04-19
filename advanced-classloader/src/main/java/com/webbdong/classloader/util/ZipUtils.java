package com.webbdong.classloader.util;

import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * ZIP 工具类
 * @author Webb Dong
 * @date 2021-02-27 11:10 AM
 */
public class ZipUtils {

    /**
     * 解压缩 zip 包并返回 byte 数组
     * @param file zip 文件对象
     * @param name 要从 zip 中获取文件的文件名
     * @return 返回 zip 包中指定文件名的 byte 数组，如果没有在 zip 包中找到指定的文件，则返回 null
     */
    @SneakyThrows
    public static byte[] unzipFileToBytes(File file, String name) {
        try (ZipFile zipFile = new ZipFile(file, StandardCharsets.UTF_8)) {
            ZipEntry entry = zipFile.getEntry(name);
            if (entry == null) {
                return null;
            }

            try (InputStream in = zipFile.getInputStream(entry)) {
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                return bytes;
            }
        }
    }

    private ZipUtils() {}

}
