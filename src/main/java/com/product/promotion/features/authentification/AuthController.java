package com.product.promotion.features.authentification;

import com.product.promotion.features.client.ClientDto;
import com.product.promotion.features.client.ClientService;
import com.product.promotion.features.producer.ProducerDto;
import com.product.promotion.features.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final ClientService clientService;
    private final ProducerService producerService;

    @Autowired
    public AuthController(ClientService authService, ProducerService producerService) {
        this.clientService = authService;
        this.producerService = producerService;
    }


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String jwt = clientService.authenticate(loginRequest);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping(path = "/signup/client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> registerClient(@RequestBody ClientDto dto) {
        return ResponseEntity.ok(clientService.register(dto));
    }

    @PostMapping(path = "/signup/producer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerDto> registerProducer(@RequestBody ProducerDto dto) {
        return ResponseEntity.ok(producerService.register(dto));
    }

}
