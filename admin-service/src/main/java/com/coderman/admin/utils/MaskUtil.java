package com.coderman.admin.utils;

/**
 * @author ：zhangyukang
 * @date ：2024/09/26 12:18
 */
public class MaskUtil {

    /**
     * 隐藏手机号，格式：138****1234
     * @param phone 待隐藏的手机号
     * @return 隐藏后的手机号
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 11) {
            return phone; // 返回原始手机号
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 隐藏邮箱，格式：jasonh***@yswg.com.cn
     * @param email 待隐藏的邮箱
     * @return 隐藏后的邮箱
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email; // 返回原始邮箱
        }
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        if (username.length() <= 3) {
            return "***@" + domain; // 用户名长度不足，返回星号
        }

        return username.substring(0, username.length() - 3) + "***@" + domain;
    }
}
