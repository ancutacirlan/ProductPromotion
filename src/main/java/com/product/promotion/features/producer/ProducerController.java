package com.product.promotion.features.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/producer")
public class ProducerController {

    private ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerDto> create(@RequestBody ProducerDto dto) {
        return ResponseEntity.ok(producerService.register(dto));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProducerDto>> getAll() {
        return ResponseEntity.ok(producerService.getAll());
    }

    @GetMapping(path = "/all/active/producers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProducerDto>> getAllActiveProducer() {
        return ResponseEntity.ok(producerService.getAllActiveProducer());
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerDto> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(producerService.getById(id));
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerDto> update(@RequestBody ProducerDto dto) {
        return ResponseEntity.ok(producerService.update(dto));
    }

    @DeleteMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(producerService.delete(id));
    }

}
