package com.example.spring.feignclient.client;

import com.example.spring.feignclient.dto.DataRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "exampleClient", url = "${feign-data.url}")
// OpenFeign 라이브러리를 통해 HTTP 클라이언트를 정의하는 어노테이션
// url에 직접 url을 입력해도 됨
// FeignClient가 interface의 구현체를 구현해 놓음
public interface ExampleClient {

    // GET 요청 (데이터 조회)
    @GetMapping("/api/data/{id}")
    String getData(@PathVariable("id") Long id);

    // POST 요청 (데이터 생성)
    @PostMapping("/api/data")
    String createData(@RequestBody DataRequestDTO dataRequestDTO);

    @PutMapping("/api/data/{id}")
    String updateData(@PathVariable("id") Long id, @RequestBody DataRequestDTO dataRequestDTO);

    @DeleteMapping("/api/data/{id}")
    String deleteData(@PathVariable("id") Long id);

    @GetMapping("/api/data")
    String getAllData();

    /*
    @DeleteMapping("/api/data/{id}")
    String deleteData(@PathVariable("id") Long id);

    @GetMapping("/api/data/all")
    List<DataResponseDTO> getAllData();
     */
}
