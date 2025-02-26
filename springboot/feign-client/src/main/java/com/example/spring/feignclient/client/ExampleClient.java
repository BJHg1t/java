package com.example.spring.feignclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exampleClient", url = "${feign-data.url}")
// OpenFeign 라이브러리를 추가했기 때문에 사용 가능, url에 직접 url 입력해도 됨
// FeignClient가 interface의 구현체를 구현해 놓았음
public interface ExampleClient {

    // Get 요청(데이터 조회)
    @GetMapping("/api/data/{id}")
    String getData(@PathVariable("id") Long id);
}
