package com.example.circuitbreaker.listener;

import com.example.circuitbreaker.service.BackendService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloWorldListener {

  public static final String HELLO_WORLD_LISTENER = "hello_world_listener";
  private final BackendService backendService;

  @JmsListener(
      id = HELLO_WORLD_LISTENER,
      destination = "hello.v1.world",
      concurrency = "5-10")
  @Timed(value = "hello_world_timer")
  public void listenForMessages(
      @Payload final String payload, @Headers final MessageHeaders messageHeaders) {
    log.info(
        String.format(
            "Hello world payload = %s and redelivered %s",
            payload, messageHeaders.get(JmsHeaders.REDELIVERED)));
    try {
      backendService.makeRequest();
    }
    catch (Exception exc) {
      log.error("FailedRequest {}", exc.getMessage());
      throw exc;
    }
  }
}
