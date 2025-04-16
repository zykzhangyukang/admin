package com.coderman.admin.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件分片哈希工具类，支持 File 和 MultipartFile。
 * 支持结构化哈希：对每个分片计算 hash，再拼接所有分片 hash 进行最终 hash。
 *
 * @author ：zhangyukang
 * @date ：2025/04/16
 */
public class FileHashUtils {

    /**
     * 默认分片大小：1MB（1024 * 1024 字节）
     */
    public static final int DEFAULT_CHUNK_SIZE = 1024 * 1024;

    /**
     * 将 byte[] 转换为十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    /**
     * 对 File 文件进行结构化 hash
     *
     * @param file      待处理文件
     * @param chunkSize 分片大小（字节）
     * @return 整个文件的结构化 hash
     */
    public static String calculateStructuredFileHash(File file, int chunkSize) throws IOException, NoSuchAlgorithmException {
        List<String> chunkHashes = getChunkHashesFromFile(file, chunkSize);
        return calculateFinalHashFromChunkHashes(chunkHashes);
    }

    /**
     * 对 MultipartFile 文件进行结构化 hash（不落盘）
     *
     * @param multipartFile 上传文件
     * @param chunkSize     分片大小（字节）
     * @return 整个文件的结构化 hash
     */
    public static String calculateFileHash(MultipartFile multipartFile, int chunkSize)
            throws IOException, NoSuchAlgorithmException {

        List<String> chunkHashes = new ArrayList<>();
        MessageDigest finalDigest = MessageDigest.getInstance("MD5");

        byte[] buffer = new byte[chunkSize];
        try (InputStream is = multipartFile.getInputStream()) {
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                MessageDigest chunkDigest = MessageDigest.getInstance("MD5");
                chunkDigest.update(buffer, 0, bytesRead);
                String chunkHash = bytesToHex(chunkDigest.digest());
                chunkHashes.add(chunkHash);
            }
        }

        for (String chunkHash : chunkHashes) {
            finalDigest.update(chunkHash.getBytes(StandardCharsets.UTF_8));
        }

        return bytesToHex(finalDigest.digest());
    }

    /**
     * 获取 File 文件的所有分片的 MD5 hash
     */
    public static List<String> getChunkHashesFromFile(File file, int chunkSize) throws IOException, NoSuchAlgorithmException {
        List<String> chunkHashes = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long fileLength = file.length();
            long offset = 0;
            byte[] buffer = new byte[chunkSize];

            while (offset < fileLength) {
                int bytesToRead = (int) Math.min(chunkSize, fileLength - offset);
                raf.seek(offset);
                raf.readFully(buffer, 0, bytesToRead);

                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(buffer, 0, bytesToRead);
                chunkHashes.add(bytesToHex(md.digest()));
                offset += bytesToRead;
            }
        }
        return chunkHashes;
    }

    /**
     * 根据分片 hash 列表计算最终 hash
     */
    public static String calculateFinalHashFromChunkHashes(List<String> chunkHashes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        for (String chunkHash : chunkHashes) {
            md.update(chunkHash.getBytes(StandardCharsets.UTF_8));
        }
        return bytesToHex(md.digest());
    }
}
