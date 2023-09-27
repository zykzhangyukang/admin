/**
 * @author ：zhangyukang
 * @date ：2023/09/14 10:45
 */
public class DesUtil {

    public static void main(String[] args) {
        String encrypt = com.coderman.service.util.DesUtil.encrypt("123456","1123456");
        System.out.println(encrypt);
    }
}
