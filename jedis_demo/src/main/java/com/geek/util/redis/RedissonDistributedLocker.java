//package com.geek.util.redis;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//
//import java.util.concurrent.TimeUnit;
//
//public class RedissonDistributedLocker implements DistributedLocker {
//
//    private RedissonClient redissonClient;
//
//    @Override
//    public void lock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock();
//    }
//
//    @Override
//    public void unlock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.unlock();
//    }
//
//    @Override
//    public void lock(String lockKey, int leaseTime) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock(leaseTime, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public void lock(String lockKey, TimeUnit unit, int timeout) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock(timeout, unit);
//    }
//
//    @Override
//    public boolean tryLock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        return lock.tryLock();
//    }
//
//    public void setRedissonClient(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//}
