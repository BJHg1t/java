package com.example.spring.webfrontservice.service;

import com.example.spring.webfrontservice.client.AuthClient;
import com.example.spring.webfrontservice.client.CatalogClient;
import com.example.spring.webfrontservice.dto.JoinClientResponseDTO;
import com.example.spring.webfrontservice.dto.JoinRequestDTO;
import com.example.spring.webfrontservice.dto.LoginClientResponseDTO;
import com.example.spring.webfrontservice.dto.LoginRequsetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthClient authClient;
    private final CatalogClient catalogClient;

    public JoinClientResponseDTO join(JoinRequestDTO joinRequestDTO) {
        return authClient.join(joinRequestDTO);
    }

    public LoginClientResponseDTO login(LoginRequsetDTO loginRequsetDTO) {
        return authClient.login(loginRequsetDTO);
    }

}
