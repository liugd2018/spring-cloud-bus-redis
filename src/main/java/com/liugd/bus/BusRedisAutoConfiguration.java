package com.liugd.bus;

import com.liugd.bus.bridge.RedisBusBridge;
import com.liugd.bus.redis.RedisStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * TODO
 *
 * @author <a href="mailto:liugd2020@gmail.com">liuguodong</a>
 * @since 1.0
 */
@Configuration
public class BusRedisAutoConfiguration {


    @Bean
    public RedisStream redisStream(RedisTemplate redisTemplate, RedisBusConfig redisBusConfig){
        return new RedisStream(redisTemplate, redisBusConfig);
    }


    @Bean
    public RedisBusBridge busBridge(RedisStream redisStream){
        return new RedisBusBridge(redisStream);
    }

}
