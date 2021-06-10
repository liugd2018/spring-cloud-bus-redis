package com.liugd.bus.bridge;

import com.liugd.bus.redis.RedisStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * TODO
 *
 * @author <a href="mailto:liugd2020@gmail.com">liuguodong</a>
 * @since 1.0
 */
public class RedisBusBridge implements BusBridge {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    private RedisStream redisStream;

    public RedisBusBridge(RedisStream redisStream) {
        this.redisStream = redisStream;
    }

    @Override
    public void send(RemoteApplicationEvent event) {

        redisStream.publishMessage(event);
    }
}
