package com.example.circuitbreaker.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.BASIC;
  }
}