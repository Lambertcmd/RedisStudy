package com.geek.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @ClassName DoSecKillService
 * @Description TODO
 * @Author Lambert
 * @Date 2021/10/27 14:49
 * @Version 1.0
 **/
@Service
public class DoSecKillService {
    public boolean doSecKill(String uid,String prodId){
        //1、判断 uid和prodId是否为空

        //2、拼接库存key和秒杀成功用户key

        //3、监视库存（增加乐观锁）
        //   3-1、判断库存是否为空
        //   3-2、判断库存是否已被抢光

        //4、获取时间，如果未到指定时间，秒杀还没开始

        //5、判断用户是否重复秒杀操作

        //6、判断如果库存数量小于1，秒杀 结束

        //7、秒杀过程

        //  7-1、增加事务

        //  7-2、库存-1

        //  7-3、把秒杀成功的用户添加到清单列表


        //  7-4、判断事务提交是否失败
        return true;
    }
}
