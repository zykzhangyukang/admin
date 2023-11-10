/**
 * @author ：zhangyukang
 * @date ：2023/09/14 10:45
 */
public class DesUtil {

    public static void main(String[] args) {
        String encrypt = com.coderman.service.util.DesUtil.encrypt("es.coderman.love:443","fdasfsf");
        System.out.println(encrypt);
        String decrypt = com.coderman.service.util.DesUtil.decrypt("FFA413173ED6FAA15A70EF283CD06271987E71D6A0ED1F3C", "fdsafdsrf");
        System.out.println(decrypt);
    }
}
