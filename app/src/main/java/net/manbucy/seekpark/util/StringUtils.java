package net.manbucy.seekpark.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by yang on 2017/6/20.
 */

public class StringUtils {

    /**
     * 验证用户名是否符合要求
     * 要求:用户名中汉字不能超过7个单词字符不能超过14个
     *
     * @param username 用户名
     * @return 是否符合要求
     */
    public static boolean verifyUsername(String username) {
        int len = username.getBytes().length;
        if (len > 35) {
            return false;
        }
        Pattern patternCN = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matcherCN = patternCN.matcher(username);
        int countCN = 0;
        while (matcherCN.find()) {
            countCN++;
        }
        Pattern patternUK = Pattern.compile("[A-Za-z0-9_]");
        Matcher matcherUK = patternUK.matcher(username);
        int countUK = 0;
        while (matcherUK.find()) {
            countUK++;
        }
        if ((countCN * 3 + countUK) != len) {
            return false;
        }
        if (countCN > 7 || countUK > 14) {
            return false;
        }
        return true;
    }

    /**
     * 验证密码是否符合要求
     * 字母、数字、_ ｛6-16｝
     * ^[\w.!~@#$%^&*]{6,16}$
     *
     * @param password 密码
     * @return 是否符合要求
     */
    public static boolean verifyPassword(String password){
        return password.matches("^[A-Za-z0-9_.!~@#$%^&*]{6,16}$");
    }

    /**
     * 验证手机号码是否符合要求
     * 11位数字 且首位是 1
     * @param phone 手机号码
     * @return 是否符合要求
     */
    public static boolean verifyPhoneNumber(String phone){
        return phone.matches("1\\d{10}");
    }

    /**
     * 验证 验证码是否符合要求
     * 6位数字
     * @param code 验证码
     * @return 是否符合要求
     */
    public static boolean verifyCAPTCHA(String code){
        return code.matches("\\d{6}");
    }
}
