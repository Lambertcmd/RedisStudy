//package com.geek.jedis;
//
//import org.apache.commons.lang3.StringUtils;
//import redis.clients.jedis.Jedis;
//
//import java.util.Random;
//
///**
// * @ClassName PhoneCode
// * @Description
// * 要求：1、输入手机号，点击发送后随机生成6位数字码，2分钟有效
// *      2、输入验证码，点击验证，返回成功或失败
// *      3、每个手机号每天只能输入3次
// * @Author Lambert
// * @Date 2021/10/22 9:35
// * @Version 1.0
// **/
//public class PhoneCode {
//    public static void main(String[] args) {
//
////        sendCode("10086");
//        verifyCode("10086", "021808");
//
//    }
//    //1.发送验证码  每个手机号每天只能输入3次,验证码放到redis中，设置过期时间
//    public static void sendCode(String phone){
//        Jedis jedis = new Jedis("10.1.53.169", 6379);
//        //手机发送次数key
//        String countKey = "VerifyCode"+phone+":count";
//        //验证码key
//        String codeKey = "VerifyCode"+phone+":code";
//        //每个手机每天只能发送三次
//        String count = jedis.get(countKey);
//        if(count == null){
//            //没有发送次数 第一次发送
//            //设置发送次数
//            jedis.setex( countKey, 24*60*60, "1");
//            //生成随机验证码
//            String code = RandomNumberGenerator.getRandomNumberByLength(6);
//            //发送的验证码放到redis
//            jedis.setex(codeKey, 120, code);
//        }else if (Integer.parseInt(count) <= 2){
//            jedis.incr(countKey);
//            //生成随机验证码
//            String code = RandomNumberGenerator.getRandomNumberByLength(6);
//            //发送的验证码放到redis
//            jedis.setex(codeKey, 120, code);
//        }else {
//            System.out.println("发送次数已达上限！");
//        }
//        jedis.close();
//    }
//
//    //2.校验验证码
//    public static void verifyCode(String phone,String code){
//        Jedis jedis = new Jedis("10.1.53.169", 6379);
//        //验证码key
//        String codeKey = "VerifyCode"+phone+":code";
//        //手机发送次数key
//        String countKey = "VerifyCode"+phone+":count";
//        String redisCode = jedis.get(codeKey);
//
//        if (StringUtils.isBlank(redisCode)){
//            System.out.println("验证码已过期");
//        }else if (StringUtils.equals(code, redisCode)){
//            System.out.println("验证码正确");
//            jedis.del(countKey);
//            jedis.del(codeKey);
//        }else {
//            System.out.println("验证码不正确");
//        }
//        jedis.close();
//    }
//}
