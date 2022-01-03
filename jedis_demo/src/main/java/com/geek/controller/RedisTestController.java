package com.geek.controller;

import com.geek.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName Redis
 * @Description TODO
 * @Author Lambert
 * @Date 2021/10/26 9:22
 * @Version 1.0
 **/
@RestController
public class RedisTestController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/redisTest")
    public String testRedis(String pattern){
//        iGlobalCache.set("name", "lucy");
        String name = (String)redisUtils.get(pattern);
        return name;
    }

//    @Autowired
//    private RedisUtils redisUtils;
//
//    @GetMapping("/redisTest")
//    public String testRedis(){
////        redisUtils.set("apartment", "programmer");
//        String name = (String)redisUtils.get("k2");
//        return name;
//    }

}
