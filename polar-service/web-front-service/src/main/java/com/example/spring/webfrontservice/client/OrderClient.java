package com.example.spring.webfrontservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orderClient", url = "${polar.edge-service-url}/orders")
public interface OrderClient {
}
