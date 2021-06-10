package com.liugd.bus.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liugd.bus.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.bus.BusConsumer;
import org.springframework.cloud.bus.event.*;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 监听时间
 *
 * @author <a href="mailto:liugd2020@gmail.com">liuguodong</a>
 * @since 1.0
 */
@Component
public class RedisChannelListener implements MessageListener{

    private Log log= LogFactory.getLog(RedisChannelListener.class);

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("============>>>>> onMessage");
        byte[] channel=message.getChannel();
        byte[] body=message.getBody();
        String title=new String(channel, StandardCharsets.UTF_8);
        String content=new String(body, StandardCharsets.UTF_8);
        log.info("消息频道名称："+title);
        log.info("消息内容是:"+content);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(RefreshRemoteApplicationEvent.class);
        objectMapper.registerSubtypes(EnvironmentChangeRemoteApplicationEvent.class);
        objectMapper.registerSubtypes(AckRemoteApplicationEvent.class);
        objectMapper.registerSubtypes(UnknownRemoteApplicationEvent.class);

        BusConsumer busConsumer = SpringUtil.getBean(BusConsumer.class);

        RemoteApplicationEvent remoteApplicationEvent = null;
        try {
            remoteApplicationEvent = objectMapper.readValue(content,RemoteApplicationEvent.class );
        } catch (JsonProcessingException e) {
            log.error("objectMapper.readValue error!");
            throw new RuntimeException("objectMapper.readValue error!");
        }

        busConsumer.accept(remoteApplicationEvent);

        log.info("============>>>>> onMessage end");

    }

}
