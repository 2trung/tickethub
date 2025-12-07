package org.tickethub.controller.http;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.tickethub.application.service.event.EventAppService;

import java.security.SecureRandom;

@RestController
public class HiController {

    private final EventAppService eventAppService;
    private final RestTemplate restTemplate = new RestTemplate();

    public HiController(EventAppService eventAppService) {
        this.eventAppService = eventAppService;
    }


    @GetMapping("/hi")
    @RateLimiter(name = "backendA", fallbackMethod = "fallbackHello")
    public String hello() {
        return eventAppService.sayHi("Hi");
    }

    public String fallbackHello() {
        return "Too many request";
    }

    @GetMapping("/hi/v1")
    @RateLimiter(name = "backendB", fallbackMethod = "fallbackHello")
    public String sayHi() {
        return eventAppService.sayHi("Ho");
    }

    private static final SecureRandom secureRandom = new SecureRandom();
    @GetMapping("/circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackCircuitBreaker")
    public String circuitBreaker() {
        int productId = secureRandom.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + productId;
        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackCircuitBreaker() {
        return "Service fakestoreapi Error!";
    }
}
