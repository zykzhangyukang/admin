import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 10:06
 */
public class DirTest {

    public static void main(String[] args) throws IOException {

        FFmpeg ffmpeg = new FFmpeg("E:\\workspace\\admin\\tools\\ffmpeg-win.exe");

        // 输入视频文件路径
        String inputVideoPath = "C:\\Users\\Administrator\\Desktop\\test\\input.mp4";

        // 输出M3U8文件路径
        String outputM3U8Path = "C:\\Users\\Administrator\\Desktop\\test\\output\\output.m3u8";

        // 创建FFmpeg命令构建器
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputVideoPath)
                .addOutput(outputM3U8Path)
                .setFormat("hls") // 指定输出格式为HLS
                .addExtraArgs("-hls_time", "1") // 设置切片时长（秒）
                .done();

        try {

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            executor.createJob(builder).run();
            System.out.println("M3U8 conversion completed.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("M3U8 conversion failed: " + e.getMessage());
        }
    }
}
