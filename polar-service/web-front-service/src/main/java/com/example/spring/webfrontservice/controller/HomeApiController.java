package com.example.spring.webfrontservice.controller;

import com.example.spring.webfrontservice.dto.*;
import com.example.spring.webfrontservice.service.CatalogService;
import com.example.spring.webfrontservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webs/api/catalog")
public class HomeApiController {

    private final CatalogService catalogService;
    private final OrderService orderService;

    @PostMapping
    public CreateCatalogResponseDTO createCatalog(
            @RequestHeader(value = AUTHORIZATION, required = false) String accessToken,
            @RequestBody CreateCatalogRequestDTO createCatalogRequestDTO
    ) {
        return catalogService.createCatalog(accessToken, createCatalogRequestDTO);
    }

    @GetMapping
    public ReadCatalogResponseDTO[] readCatalog(@RequestHeader(value = AUTHORIZATION, required = false) String accessToken) {
        return catalogService.readCatalog(accessToken);
    }

    @PostMapping("/orders")
    public CreateOrderResponseDTO createOrder(
            @RequestHeader(value = AUTHORIZATION, required = false) String accessToken,
            @RequestBody CreateOrderRequestDTO createOrderRequestDTO
    ) {
        return orderService.createOrder(accessToken, createOrderRequestDTO);
    }

}
