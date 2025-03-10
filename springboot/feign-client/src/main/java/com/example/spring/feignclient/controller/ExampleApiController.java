package com.example.spring.feignclient.controller;

import com.example.spring.feignclient.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
// 서버간 통신할 때 JSON형식을 이용하면 @RestController
// 화면 이동할 때 @Controller
@RequiredArgsConstructor
@RequestMapping("/feign/data")
public class ExampleApiController {

    private final ExampleService exampleService;

    @GetMapping("/{id}")
    public String getData(@PathVariable Long id) {
        return exampleService.getDataById(id);
    }

    @PostMapping
    public String createData(@RequestParam String name, @RequestParam int value) {
        return exampleService.createData(name, value);
    }

    @PutMapping("/{id}")
    public String updateData(@PathVariable Long id, @RequestParam String name, @RequestParam int value) {
        return exampleService.updateDataById(id, name, value);
    }

    @DeleteMapping("/{id}")
    public String deleteData(@PathVariable Long id) {
        return exampleService.deleteDataById(id);
    }

    @GetMapping
    public String getAll() {
        return exampleService.getAll();
    }

    /*
    @GetMapping("/all")
    public List<DataResponseDTO> getAllData() {
        return exampleService.getAllData();
    }
    */
}
