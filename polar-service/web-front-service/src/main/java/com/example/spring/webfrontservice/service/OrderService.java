package com.example.spring.webfrontservice.service;

import com.example.spring.webfrontservice.client.OrderClient;
import com.example.spring.webfrontservice.dto.CreateOrderRequestDTO;
import com.example.spring.webfrontservice.dto.CreateOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderClient orderClient;

    public CreateOrderResponseDTO createOrder(
            String accessToken,
            CreateOrderRequestDTO createOrderRequestDTO
    ) {
        return orderClient.createOrder(accessToken, createOrderRequestDTO);
    }

}
