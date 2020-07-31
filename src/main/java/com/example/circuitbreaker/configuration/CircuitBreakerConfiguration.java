package com.example.circuitbreaker.configuration;

import com.example.circuitbreaker.service.CircuitBreakerEventHandler;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CircuitBreakerConfiguration {

  private final CircuitBreakerRegistry circuitBreakerRegistry;
  private final CircuitBreakerEventHandler circuitBreakerEventHandler;

  @PostConstruct
  public void registerEventConsumer() {
    circuitBreakerRegistry
        .circuitBreaker("backendService")
        .getEventPublisher()
        .onStateTransition(circuitBreakerEventHandler::onStateChange);
  }
}
