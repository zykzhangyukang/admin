package com.coderman.admin.utils;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class WordToPdfUtil {

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 30000;

    /**
     * 将 Word 文件转换为 PDF 文件（文件路径方式）
     * @param inputPath Word 文件路径
     * @param outputPath 输出 PDF 路径
     * @throws Exception 转换失败抛出异常
     */
    public static void convertToPdf(String inputPath, String outputPath) throws Exception {
        try {
            Document doc = new Document(inputPath);
            doc.save(outputPath, SaveFormat.PDF);
            log.info("Word 文件成功转换为 PDF: {}", outputPath);
        } catch (Exception e) {
            log.error("转换 Word 文件为 PDF 失败，路径: {}", inputPath, e);
            throw e;
        }
    }

    /**
     * 将 Word 文件流转换为 PDF 并输出到流
     * @param inputStream Word 输入流
     * @param outputStream PDF 输出流
     * @throws Exception 转换失败抛出异常
     */
    public static void convertToPdf(InputStream inputStream, OutputStream outputStream) throws Exception {
        try {
            Document doc = new Document(inputStream);
            doc.save(outputStream, SaveFormat.PDF);
            log.info("Word 文件流成功转换为 PDF.");
        } catch (Exception e) {
            log.error("转换 Word 文件流为 PDF 失败", e);
            throw e;
        }
    }

    /**
     * 获取网络上的 Word 文件转成 PDF 并保存为文件
     * 直接从网络流中读取并转换为 PDF，无需临时文件
     * @param fileUrl 网络 Word 文件 URL
     * @param outputPdfPath 输出 PDF 文件路径
     * @throws Exception 转换失败抛出异常
     */
    public static void convertUrlToPdf(String fileUrl, String outputPdfPath) throws Exception {
        HttpURLConnection connection = null;

        try {
            connection = createHttpConnection(fileUrl);
            // 直接从输入流中读取并转换为 PDF
            try (InputStream inputStream = connection.getInputStream()) {
                Document doc = new Document(inputStream);
                doc.save(outputPdfPath, SaveFormat.PDF);
                log.info("网络 Word 文件成功转换为 PDF, 输出路径: {}", outputPdfPath);
            }

        } catch (Exception e) {
            log.error("网络 Word 文件转换为 PDF 失败: {}", fileUrl, e);
            throw e;
        } finally {
            cleanupConnection(connection);
        }
    }

    /**
     * 将网络上的 Word 文件转换为 PDF 并直接输出到浏览器响应流
     * 直接从网络流中读取并输出 PDF 到浏览器
     * @param fileUrl 文件 URL 地址
     * @param response HttpServletResponse 用于输出 PDF
     * @throws Exception 转换失败抛出异常
     */
    public static void convertUrlToPdfToResponseStream(String fileUrl, HttpServletResponse response) throws Exception {
        HttpURLConnection connection = null;

        try {
            connection = createHttpConnection(fileUrl);
            // 获取输入流并转换为 PDF 输出到浏览器
            try (InputStream inputStream = connection.getInputStream();
                 OutputStream outputStream = response.getOutputStream()) {

                // 设置响应头，告知浏览器是 PDF 文件
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\"output.pdf\"");

                Document doc = new Document(inputStream);
                doc.save(outputStream, SaveFormat.PDF);

                log.info("网络 Word 文件成功转换为 PDF 并输出到浏览器.");
            }

        } catch (Exception e) {
            log.error("网络 Word 文件转换为 PDF 并输出到浏览器失败: {}", fileUrl, e);
            throw e;
        } finally {
            cleanupConnection(connection);
        }
    }

    /**
     * 创建并配置 HttpURLConnection
     * @param fileUrl 文件 URL
     * @return 配置好的 HttpURLConnection
     * @throws IOException
     */
    private static HttpURLConnection createHttpConnection(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP 请求失败，响应码：" + responseCode);
        }
        return connection;
    }

    /**
     * 清理资源
     * @param connection HTTP 连接
     */
    private static void cleanupConnection(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    /**
     * 判断文件是否为支持的 Word 格式
     * @param fileName 文件名
     * @return true 表示是 Word 文件
     */
    public static boolean isWordFile(String fileName) {
        if (fileName == null) return false;
        String lower = fileName.toLowerCase();
        return lower.endsWith(".doc") || lower.endsWith(".docx") || lower.endsWith(".dot") || lower.endsWith(".dotx");
    }
}
