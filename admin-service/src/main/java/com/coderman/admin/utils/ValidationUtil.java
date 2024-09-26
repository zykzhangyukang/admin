package com.coderman.admin.utils;

import java.util.regex.Pattern;

/**
 * @author ：zhangyukang
 * @date ：2024/09/26 11:17
 */
public class ValidationUtil {

    // 手机号正则表达式（中国大陆手机号）
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    // 邮箱正则表达式
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}$";
    // URL正则表达式（小写字母，不能包含数字）
    private static final String URL_REGEX = "^/[a-z]+(/[a-z]+){0,5}$";
    // 姓名正则表达式（中文姓名）
    private static final String NAME_REGEX = "^[\\u4e00-\\u9fa5]{2,20}$";
    // 密码强度正则表达式（至少8位，包含大写字母、小写字母、数字和特殊字符）
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    // 用户账号正则表达式（3到20个字符，以字母开头，可以包含字母、数字、下划线和连字符）
    private static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_-]{2,19}$";

    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);

    /**
     * 校验手机号
     * @param phone 待校验的手机号
     * @return 如果手机号合法返回 true，否则返回 false
     */
    public static boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 校验邮箱
     * @param email 待校验的邮箱
     * @return 如果邮箱合法返回 true，否则返回 false
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 校验URL
     * @param url 待校验的URL地址
     * @return 如果URL合法返回 true，否则返回 false
     */
    public static boolean isValidUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }

    /**
     * 校验中文姓名的合法性。
     * 姓名必须是 2 到 20 个中文字符。
     *
     * @param name 待校验的姓名
     * @return 如果姓名合法返回 true，否则返回 false
     */
    public static boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    /**
     * 校验密码的强度。
     * 密码必须至少 8 位，并且包含至少一个大写字母、一个小写字母、一个数字和一个特殊字符。
     *
     * @param password 待校验的密码
     * @return 如果密码符合强度要求返回 true，否则返回 false
     */
    public static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * 校验用户账号的合法性。
     * 用户账号必须是 3 到 20 个字符，以字母开头，可以包含字母、数字、下划线和连字符。
     *
     * @param username 待校验的用户账号
     * @return 如果用户账号合法返回 true，否则返回 false
     */
    public static boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }
}
