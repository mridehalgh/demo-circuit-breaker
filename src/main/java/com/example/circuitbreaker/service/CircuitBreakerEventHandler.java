package com.example.circuitbreaker.service;

import static com.example.circuitbreaker.listener.HelloWorldListener.HELLO_WORLD_LISTENER;

import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CircuitBreakerEventHandler {

  private final JmsListenerEndpointRegistry registry;

  public void onStateChange(final CircuitBreakerOnStateTransitionEvent event) {
    log.info("Triggered State Change");
    State toState = event.getStateTransition().getToState();
    DefaultMessageListenerContainer listenerContainer = (DefaultMessageListenerContainer) registry
        .getListenerContainer(HELLO_WORLD_LISTENER);

    switch (toState) {
      case OPEN:
        log.info("StoppingConsuming");
        Objects.requireNonNull(registry.getListenerContainer(HELLO_WORLD_LISTENER)).stop();
        break;
      case CLOSED:
        log.info("OpeningFloodGates");
        assert listenerContainer != null;
        listenerContainer.setConcurrentConsumers(15);
        listenerContainer.setMaxConcurrentConsumers(30);
        listenerContainer.setConcurrency("15-30");
        listenerContainer.start();
        break;
      case HALF_OPEN:
        log.info("StartConsumingSlowly");
        assert listenerContainer != null;
        listenerContainer.setConcurrentConsumers(1);
        listenerContainer.setMaxConcurrentConsumers(1);
        listenerContainer.setConcurrency("1-1");
        listenerContainer.start();
        break;
    }
  }
}
