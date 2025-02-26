package com.example.spring.feigndata.controller;

import com.example.spring.feigndata.dto.DataResponseDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController //스프링 자체가 관리하겠다
@RequestMapping("/api/data")
public class DataController {

    private Map<Long, DataResponseDTO> dataStore = new HashMap<>();
    private Long idCnt = 1L;

    //초기 데이터를 추가하는 메서드
    @PostConstruct
    public void initDataSource() {
    //동일한 메서드, new를 사용했을 때 name에 value값을 넣는 등 실수할 수 있음
        /*dataStore.put(
                idCnt++, DataResponseDTO.builder()
                            .id(1L)
                            .name("Item 1")
                            .value(100)
                        .build()
        );*/
        dataStore.put(idCnt++, new DataResponseDTO(1L,"Item 1", 100));
        dataStore.put(idCnt++, new DataResponseDTO(1L,"Item 2", 200));
        dataStore.put(idCnt++, new DataResponseDTO(1L,"Item 3", 300));
        dataStore.put(idCnt++, new DataResponseDTO(1L,"Item 4", 400));
        dataStore.put(idCnt++, new DataResponseDTO(1L,"Item 5", 500));

    }

    @GetMapping("/{id}")
    public DataResponseDTO get(@PathVariable Long id) {
        return dataStore.get(id);
    }
}
