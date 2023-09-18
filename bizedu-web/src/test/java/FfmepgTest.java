import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 10:28
 */
public class FfmepgTest {

    public static void main(String[] args) throws IOException {

        // 初始化FFmpeg
        FFmpeg ffmpeg = new FFmpeg("E:\\workspace\\bizedu\\tools\\ffmpeg-win.exe");

        // 输入视频文件路径
        String inputVideoPath = "C:\\Users\\Administrator\\Desktop\\test\\input.mp4";

        // 输出M3U8文件路径
        String outputM3U8Path = "C:\\Users\\Administrator\\Desktop\\test\\output\\output.m3u8";


        //下面这一串参数别乱动，经过调优的，1G视频大概需要10秒左右，如果是大佬随意改
        //"-vsync", "2", "-c:v", "copy", "-c:a", "copy", "-tune", "fastdecode", "-hls_wrap", "0", "-hls_time", "10", "-hls_list_size", "0", "-threads", "12"
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputVideoPath)
                .addOutput(outputM3U8Path)
                .setFormat("hls") // 指定输出格式为HLS
                .addExtraArgs("-hls_time", "10") // 设置切片时长（秒）
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        // Run a one-pass encode
        executor.createJob(builder).run();
    }

}
