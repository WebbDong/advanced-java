package com.webbdong.jvm.util;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 字节码工具类
 * @author Webb Dong
 * @date 2021-01-07 16:12
 */
public class ByteCodeUtils {

    private static final int BUFFER_SIZE = 1024;

    private ByteCodeUtils() {}

    /**
     * 解码 xlass 的字节码，将字节码文件数据的每一个字节都用 255 减去当前的字节数值获取原字节码数据
     * @param bytes 字节数组
     */
    public static void xlassDecode(byte[] bytes) {
        IntStream.range(0, bytes.length).boxed()
                .forEach(i -> bytes[i] = (byte) (255 - bytes[i]));

        /*
        IntStream.range(0, bytes.length).boxed()
                .forEach(i -> bytes[i] = (byte) ~bytes[i]);
         */
    }

    /**
     * 读取包中指定的本地字节码文件流，并且将流转换成 byte 数组
     * @param in 输入流
     * @return 返回字节码文件的 byte 数组
     */
    @SneakyThrows
    public static byte[] readLocalClassAsBytes(InputStream in) {
        byte[] bytes = new byte[in.available()];
        try (InputStream stream = in) {
            stream.read(bytes, 0, bytes.length);
        }
        return bytes;
    }

    /**
     * 获取网络中的字节码文件
     * @param urlString url
     * @return 返回字节码数据字节数组
     * @throws IOException 抛出 IO 异常
     */
    public static byte[] getRemoteClassFileAsBytesByUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(1000);
        conn.setRequestMethod("GET");
        try (InputStream in = conn.getInputStream()) {
            if (in == null) {
                throw new IOException("InputStream is null");
            }
            // 请求获取远程字节码文件
            List<Byte> byteList = new ArrayList<>();
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                IntStream.range(0, len).boxed().forEach(i -> byteList.add(buffer[i]));
            }

            // List<Byte> 转 byte[]
            Object[] boxedArray = byteList.toArray();
            len = boxedArray.length;
            byte[] bytes = new byte[boxedArray.length];
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) boxedArray[i];
            }
            return bytes;
        }
    }

}
