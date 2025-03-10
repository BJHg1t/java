package com.example.spring.feignclient.service;

import com.example.spring.feignclient.client.ExampleClient;
import com.example.spring.feignclient.dto.DataRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 생성자를 자동으로 추가해 주는 어노테이션
public class ExampleService {

    // final을 썻기 때문에 생성자를 통해 주입받아야 함 = 생성된 객체를 받아온다.
    private final ExampleClient exampleClient;

    // GET 요청 호출
    public String getDataById(Long id) {
        return exampleClient.getData(id);
    }

    public String createData(String name, int value) {
        return exampleClient.createData(
                DataRequestDTO.builder()
                        .name(name)
                        .value(value)
                        .build()
        );
    }

    public String updateDataById(Long id, String name, int value) {
        return exampleClient.updateData(id,
                DataRequestDTO.builder()
                        .name(name)
                        .value(value)
                        .build()
        );
    }

    public String deleteDataById(Long id) {
        return exampleClient.deleteData(id);
    }

    public String getAll() {
        return exampleClient.getAllData();
    }

    /*
    public List<DataResponseDTO> getAllData() {
        return exampleClient.getAllData();
    }
    */
}
