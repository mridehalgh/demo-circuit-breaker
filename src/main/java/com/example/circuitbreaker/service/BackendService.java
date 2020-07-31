package com.example.circuitbreaker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackendService {

  public static final String BACKEND_SERVICE = "backendService";
  private final BackendFeignClient backendFeignClient;

  @CircuitBreaker(name = BACKEND_SERVICE)
  public void makeRequest() {
    backendFeignClient.getStatusCode();
  }
}
