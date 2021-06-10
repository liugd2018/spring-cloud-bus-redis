package com.liugd.bus.redis;

import com.liugd.bus.RedisBusConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


/**
 * TODO
 *
 * @author <a href="mailto:liugd2020@gmail.com">liuguodong</a>
 * @since 1.0
 */
public class RedisStream {

    private RedisTemplate redisTemplate;

    private RedisBusConfig redisBusConfig;

    public RedisStream(RedisTemplate redisTemplate, RedisBusConfig redisBusConfig) {
        this.redisTemplate = redisTemplate;
        this.redisBusConfig = redisBusConfig;
    }

    public void publishMessage(RemoteApplicationEvent applicationEvent) {
        redisTemplate.convertAndSend(redisBusConfig.getQueueName(),applicationEvent);
    }



}
