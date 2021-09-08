package com.xyz.study.common.jike.week12;

import lombok.extern.slf4j.Slf4j;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author xyz
 * @date 2021/9/8
 **/
@Slf4j
public class TextMessageListener implements MessageListener {

    private String listenerName;

    public TextMessageListener(String listenerName) {
        this.listenerName = listenerName;
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            log.info("{}接收到消息 message={}", listenerName, textMessage.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
