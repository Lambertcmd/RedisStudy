package com.geek.controller;

import com.geek.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RedisTestController
 * @Description TODO
 * @Author Lambert
 * @Date 2021/10/26 14:28
 * @Version 1.0
 **/
@RestController
public class RedisTestController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/redisTest")
    public String redisTest(String pattern){
        redisUtils.set("school", pattern);
        String school = (String) redisUtils.get("school");
        return school;
    }
}
