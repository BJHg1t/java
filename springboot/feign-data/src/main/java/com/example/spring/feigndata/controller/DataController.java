package com.example.spring.feigndata.controller;

import com.example.spring.feigndata.dto.DataRequestDTO;
import com.example.spring.feigndata.dto.DataResponseDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j // 서버 올릴 때는 빼야 함
@RestController // 스프링 자체가 관리하겠다
@RequestMapping("/api/data")
public class DataController {
    // 공공 data 서버
    private Map<Long, DataResponseDTO> dataStore = new HashMap<>();
    private Long idCnt = 1L;

    // 초기 데이터를 추가하는 메서드
    @PostConstruct
    public void initDataSource() {
    // 동일한 메서드, new를 사용했을 때 name에 value값을 넣는 등 실수할 수 있음
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
        log.info("[Feign Data] select");
        return dataStore.get(id);
    }

    @PostMapping
    public DataResponseDTO createData(@RequestBody DataRequestDTO dataRequestDTO) {
        log.info("[Feign Data] create");
        DataResponseDTO newData = DataResponseDTO.builder()
                .id(idCnt)
                .name(dataRequestDTO.getName())
                .value(dataRequestDTO.getValue())
                .build();

        dataStore.put(idCnt++, newData);

        return newData;
    }

    @PutMapping("/{id}")
    public DataResponseDTO updateData(
            @PathVariable Long id,
            @RequestBody DataRequestDTO dataRequestDTO
    ) {
        log.info("[Feign Data] update");
        DataResponseDTO dataResponseDTO = dataStore.get(id);

        dataResponseDTO.setName(dataRequestDTO.getName());
        dataResponseDTO.setValue(dataRequestDTO.getValue());
        dataStore.put(id, dataResponseDTO);

        return dataResponseDTO;
    }
}
