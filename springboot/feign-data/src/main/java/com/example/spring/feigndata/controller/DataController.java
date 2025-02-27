package com.example.spring.feigndata.controller;

import com.example.spring.feigndata.dto.DataRequestDTO;
import com.example.spring.feigndata.dto.DataResponseDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j // log를 찍을 수 있게 해주는 어노테이션, 서버 올릴 때는 서버의 보안 및 성능에 영향때문에 빼야 한다.
@RestController // @Component의 특성을 포함하기에 Spring이 bean을 통해 자체가 관리한다.
@RequestMapping("/api/data") // HTTP 요청 URL과 메서드를 매핑하는 어노테이션
public class DataController {
    // 공공 data 서버
    private Map<Long, DataResponseDTO> dataStore = new HashMap<>();
    private Long idCnt = 1L;

    // 초기 데이터를 추가하는 메서드
    @PostConstruct // bean이 초기화 된 후 자동으로 호출되는 메서드를 지정하는 어노테이션, 주로 초기화, 초기값을 넣는데 사용한다.
    public void initDataSource() {
        /*
        동일한 메서드, new를 사용했을 때 name에 value값을 넣는 등 실수할 수 있음
        dataStore.put(
                idCnt++, DataResponseDTO.builder()
                            .id(1L)
                            .name("Item 1")
                            .value(100)
                        .build()
        );
        */
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

    @DeleteMapping("/{id}")
    public DataResponseDTO deleteData(@PathVariable Long id) {
        log.info("[Feign Data] delete");
        dataStore.remove(id);

        return dataStore.get(id);
    }

    @GetMapping
    public List<DataResponseDTO> getAll() {
        log.info("[Feign Data] getAll");
        List<DataResponseDTO> dataResponseDTOList = new ArrayList<>();

        for (DataResponseDTO dataResponseDTO : dataStore.values()) {
            dataResponseDTOList.add(dataResponseDTO);
        }

        return dataResponseDTOList;
    }

    /*
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        log.info("[Feign Data] delete");
        DataResponseDTO removed = dataStore.remove(id);
        if (removed != null) {
        (예외 처리)
        }

        return "Data with ID " + removed.getId() + " was deleted";
    }

    @GetMapping("/all")
    public List<DataResponseDTO> getAll() {
        log.info("[Feign Data] getAll");
        List<DataResponseDTO> results = new ArrayList<>();

        for (Long id : dataStore.keySet()) {
            DataResponseDTO dataResponseDTO = dataStore.get(id);
            results.add(dataResponseDTO);
        }

        return results;
    }
    */
}
