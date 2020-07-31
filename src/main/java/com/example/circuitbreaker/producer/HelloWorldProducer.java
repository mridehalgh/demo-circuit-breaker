package com.example.circuitbreaker.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloWorldProducer {

  private final JmsTemplate jmsTemplate;

  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
    log.info("Sending message to queue");
    jmsTemplate.convertAndSend("hello.v1.world", "{\"hello\": \"world\"}");
  }
}
