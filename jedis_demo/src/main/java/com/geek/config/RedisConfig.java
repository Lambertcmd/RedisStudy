//package com.geek.config;
//
//import cn.hutool.core.lang.Assert;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.parser.ParserConfig;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.redisson.config.SingleServerConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cache.Cache;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author xuLiang
// * @date 2020-1-24
// */
//@Slf4j
//@Configuration
//@EnableCaching
//@ConditionalOnClass(RedisOperations.class)
//@EnableConfigurationProperties(RedisProperties.class)
//public class RedisConfig extends CachingConfigurerSupport {
//
//    @Value("${spring.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.redis.port}")
//    private String redisPort;
//
//    @Value("${spring.redis.password}")
//    private String redisPassword;
//
//    @Value("${spring.redis.timeout}")
//    private Integer redisTimeout;
//
//    @Value("${spring.redis.database}")
//    private Integer redisDatabase;
//
//    @Value("${spring.redis.lettuce.pool.max-active}")
//    private Integer redisMaxActive;
//
//    @Value("${spring.redis.lettuce.pool.min-idle}")
//    private Integer redisMinIdle;
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        SingleServerConfig serverConfig = config.useSingleServer().setAddress("redis://" + this.redisHost + ":" + this.redisPort)
//                .setTimeout(this.redisTimeout)
//                .setDatabase(this.redisDatabase)
//                .setConnectionPoolSize(this.redisMaxActive)
//                .setConnectionMinimumIdleSize(this.redisMinIdle);
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(this.redisPassword)) {
//            serverConfig.setPassword(this.redisPassword);
//        }
//        return Redisson.create(config);
//    }
//    /**
//     * ??????locker???????????????????????????RedissLockUtil???
//     * @return
//     */
//    @Bean
//    DistributedLocker distributedLocker(RedissonClient redissonClient) {
//        RedissonDistributedLocker locker = new RedissonDistributedLocker();
//        locker.setRedissonClient(redissonClient);
//        RedissLockUtil.setLocker(locker);
//        return locker;
//    }
//
//    /**
//     *  ?????? redis ?????????????????????????????????2??????
//     *  ??????@cacheable ???????????????
//     */
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration(){
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
//        configuration = configuration.serializeValuesWith(RedisSerializationContext.
//                SerializationPair.fromSerializer(fastJsonRedisSerializer)).entryTtl(Duration.ofHours(6));
//        return configuration;
//    }
//
//    @SuppressWarnings("all")
//    @Bean(name = "redisTemplate")
//    @ConditionalOnMissingBean(name = "redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        //?????????
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        // value?????????????????????fastJsonRedisSerializer
//        template.setValueSerializer(fastJsonRedisSerializer);
//        template.setHashValueSerializer(fastJsonRedisSerializer);
//        // ????????????AutoType?????????????????????????????????????????????
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        // ???????????????????????????????????????????????????
//        // ParserConfig.getGlobalInstance().addAccept("com.jiabaida.domain");
//        // key??????????????????StringRedisSerializer
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//    /**
//     * ???????????????key???????????????????????????????????????
//     */
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return (target, method, params) -> {
//            Map<String,Object> container = new HashMap<>(3);
//            Class<?> targetClassClass = target.getClass();
//            // ?????????
//            container.put("class",targetClassClass.toGenericString());
//            // ????????????
//            container.put("methodName",method.getName());
//            // ?????????
//            container.put("package",targetClassClass.getPackage());
//            // ????????????
//            for (int i = 0; i < params.length; i++) {
//                container.put(String.valueOf(i),params[i]);
//            }
//            // ??????JSON?????????
//            String jsonString = JSON.toJSONString(container);
//            // ???SHA256 Hash?????????????????????SHA256????????????Key
//            return DigestUtils.sha256Hex(jsonString);
//        };
//    }
//
//    @Bean
//    @Override
//    public CacheErrorHandler errorHandler() {
//        // ??????????????????Redis??????????????????????????????????????????????????????
//        log.info("????????? -> [{}]", "Redis CacheErrorHandler");
//        return new CacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
//                log.error("Redis occur handleCacheGetError???key -> [{}]", key, e);
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
//                log.error("Redis occur handleCachePutError???key -> [{}]???value -> [{}]", key, value, e);
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
//                log.error("Redis occur handleCacheEvictError???key -> [{}]", key, e);
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException e, Cache cache) {
//                log.error("Redis occur handleCacheClearError???", e);
//            }
//        };
//    }
//
//}
//
///**
// * Value ?????????
// *
// * @author /
// * @param <T>
// */
// class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
//
//    private final Class<T> clazz;
//
//    //???????????????
//    static {
//        ParserConfig.getGlobalInstance().addAccept("com.jiabaida.modules.security.service.dto");
//    }
//
//    FastJsonRedisSerializer(Class<T> clazz) {
//        super();
//        this.clazz = clazz;
//    }
//
//    @Override
//    public byte[] serialize(T t) {
//        if (t == null) {
//            return new byte[0];
//        }
//        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
//    }
//
//    @Override
//    public T deserialize(byte[] bytes) {
//        if (bytes == null || bytes.length <= 0) {
//            return null;
//        }
//        String str = new String(bytes, StandardCharsets.UTF_8);
//        return JSON.parseObject(str, clazz);
//    }
//
//}
//
///**
// * ??????????????????
// *
// * @author /
// */
//class StringRedisSerializer implements RedisSerializer<Object> {
//
//    private final Charset charset;
//
//    StringRedisSerializer() {
//        this(StandardCharsets.UTF_8);
//    }
//
//    private StringRedisSerializer(Charset charset) {
//        Assert.notNull(charset, "Charset must not be null!");
//        this.charset = charset;
//    }
//
//    @Override
//    public String deserialize(byte[] bytes) {
//        return (bytes == null ? null : new String(bytes, charset));
//    }
//
//    @Override
//    public byte[] serialize(Object object) {
//        String string = JSON.toJSONString(object);
//        if (StringUtils.isBlank(string)) {
//            return null;
//        }
//        string = string.replace("\"", "");
//        return string.getBytes(charset);
//    }
//}
