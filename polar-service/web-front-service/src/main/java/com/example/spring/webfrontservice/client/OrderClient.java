package com.example.spring.webfrontservice.client;

import com.example.spring.webfrontservice.dto.CreateOrderRequestDTO;
import com.example.spring.webfrontservice.dto.CreateOrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "orderClient", url = "${polar.edge-service-url}/orders")
public interface OrderClient {

    @PostMapping
    CreateOrderResponseDTO createOrder(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody CreateOrderRequestDTO createOrderRequestDTO
    );
}
