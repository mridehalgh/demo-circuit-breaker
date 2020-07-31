package com.example.circuitbreaker.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "example", url = "http://localhost:9898/")
public interface BackendFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/readyz")
  void getStatusCode();
}