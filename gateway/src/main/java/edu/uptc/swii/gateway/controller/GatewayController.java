package edu.uptc.swii.gateway.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

  @GetMapping("/gateway/health")
  public Mono<Map<String, Object>> health() {
    Map<String, Object> health = new HashMap<>();
    health.put("status", "UP");
    health.put("service", "API Gateway");
    health.put("timestamp", LocalDateTime.now());
    health.put("version", "1.0.0");

    return Mono.just(health);
  }

  @GetMapping("/gateway/info")
  public Mono<Map<String, Object>> info() {
    Map<String, Object> info = new HashMap<>();
    info.put("name", "EDA Microservices API Gateway");
    info.put("description", "Gateway for Customer, Login and Order microservices");
    info.put("routes", new String[] {
        "/api/customers/* -> Customer Service (8081)",
        "/api/logins/* -> Login Service (8082)",
        "/api/orders/* -> Order Service (8083)"
    });

    return Mono.just(info);
  }
}
