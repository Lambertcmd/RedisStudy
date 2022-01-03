package com.geek.jedis;

import java.util.Random;

/**
 * 随机生成字符串
 * RandomNumberGenerator<BR>
 * 创建人:小威 <BR>
 * 时间：2015年10月16日-下午1:52:52 <BR>
 *
 * @version 1.0.0
 */
public class RandomNumberGenerator {
    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomNumberByLength(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
