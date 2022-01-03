//package com.geek.jedis;
//
//import org.junit.Test;
//import redis.clients.jedis.Jedis;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @ClassName JedisDemo1
// * @Description TODO
// * @Author Lambert
// * @Date 2021/10/21 9:15
// * @Version 1.0
// **/
//public class JedisDemo1 {
//    public static void main(String[] args) {
//        //创建Jedis对象
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//
//        //测试
//        String value = jedis.ping();
//        System.out.println(value);
//
//
//
//        jedis.close();
//    }
//    @Test
//    public void getKeys(){
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//        Set<String> keys = jedis.keys("*");
//        System.out.println(keys);
//        jedis.close();
//    }
//    //key
//    @Test
//    public void demo1(){
//        //创建Jedis对象
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//        Set<String> keys = jedis.keys("*");
//        //添加
//        jedis.set("name", "lucy");
//        //获取
//        String name = jedis.get("name");
//        System.out.println("name："+name);
//        //判断key是否存在
//        jedis.exists("name");
//        //设置key的过期时间
//        jedis.expire("name", 20);
//        //获取key的过期时间
//        Long ttl = jedis.ttl("name");
//        System.out.println("过期时间："+ttl);//20
//
//        for (String key: keys) {
//            System.out.println(key);
//        }
//        jedis.close();
//    }
//
//    //String
//    @Test
//    public void demo2(){
//        //创建Jedis对象
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//        //添加多个键值对
//        jedis.mset("k1","v1","k2","v2");
//        //获取多个键值对
//        List<String> mget = jedis.mget("k1", "k2");
//        System.out.println(mget);//[v1, v2]
//        jedis.close();
//    }
//
//    //list
//    @Test
//    public void demo3(){
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//        //从左插入list
//        jedis.lpush("key1","v1","v2","v3");
//        //获取list
//        List<String> values = jedis.lrange("key1", 0, -1);
//        System.out.println(values);//[v3, v2, v1]
//        for (String value: values) {
//            System.out.println(value);//v3,v2,v1
//        }
//        jedis.close();
//    }
//
//    //set
//    @Test
//    public void demo4(){
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//        //添加
//        jedis.sadd("names","lucy","jack");
//        //获取
//        Set<String> name = jedis.smembers("names");
//        System.out.println(name);//[lucy, jack]
//        //删除
//        jedis.srem("names", "jack");
//        Set<String> afterRemName = jedis.smembers("names");//[lucy]
//        System.out.println(afterRemName);
//        jedis.close();
//    }
//    //hash
//    @Test
//    public void demo5(){
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//        //添加
//        jedis.hset("users","age","20");
//        //获取
//        String hget = jedis.hget("users", "age");
//        System.out.println(hget);//20
//        //批量添加
//        Map<String, String> map = new HashMap<>();
//        map.put("telephone", "10086");
//        map.put("address", "geek");
//        jedis.hmset("user2", map);
//        //批量获取
//        List<String> hmget = jedis.hmget("user2", "telephone", "address");//[10086, geek]
//        System.out.println(hmget);
//        jedis.close();
//    }
//    //zset
//    @Test
//    public void demo6(){
//        Jedis jedis = new Jedis("10.1.53.169",6379);
//        //添加
//        jedis.zadd("china", 100d, "shanghai");
//        //获取
//        Set<String> zrange = jedis.zrange("china", 0, -1);
//        System.out.println(zrange);//[shanghai]
//        jedis.close();
//    }
//
//}
//
