package com.example.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserServiceClient {
    @GetMapping("/users/{id}")
    Object getUserById(@PathVariable("id") UUID id);

    @GetMapping("/users/email/{email}")
    Object getUserByEmail(@PathVariable("email") String email);
}
