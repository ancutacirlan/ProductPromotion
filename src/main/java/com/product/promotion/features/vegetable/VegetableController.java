package com.product.promotion.features.vegetable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vegetable")
public class VegetableController {

    private VegetableService vegetableService;

    @Autowired
    public VegetableController(VegetableService vegetableService) {
        this.vegetableService = vegetableService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VegetableDto> create(@RequestBody VegetableDto dto) {
        return ResponseEntity.ok(vegetableService.create(dto));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VegetableDto>> getAll() {
        return ResponseEntity.ok(vegetableService.getAll());
    }


    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VegetableDto> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(vegetableService.getById(id));
    }

    @GetMapping(path = "/name", produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public ResponseEntity<VegetableDto> getByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(vegetableService.getByName(name));
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VegetableDto> update(@RequestBody VegetableDto dto) {
        return ResponseEntity.ok(vegetableService.update(dto));
    }

    @DeleteMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(vegetableService.delete(id));
    }

}
