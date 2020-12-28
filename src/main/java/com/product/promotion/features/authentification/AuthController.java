package com.product.promotion.features.authentification;

import com.product.promotion.features.client.Client;
import com.product.promotion.features.client.ClientDto;
import com.product.promotion.features.client.ClientService;
import com.product.promotion.features.producer.ProducerDto;
import com.product.promotion.features.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        String role = clientService.getClientByEmail(loginRequest.getEmail()).getRole();
        String email = clientService.getClientByEmail(loginRequest.getEmail()).getEmail();
        Integer id = clientService.getClientByEmail(loginRequest.getEmail()).getId();
        return ResponseEntity.ok(new JwtResponse(jwt,id,email,role));
    }

    @PostMapping(path = "/signup/client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> registerClient(@RequestBody ClientDto dto) {
        return ResponseEntity.ok(clientService.register(dto));
    }

    @PostMapping(path = "/signup/producer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerDto> registerProducer(@RequestBody ProducerDto dto) {
        return ResponseEntity.ok(producerService.register(dto));
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        Optional<Client> val = Optional.ofNullable(clientService.getClientByEmail(resetPasswordRequest.getEmail()));
        val.ifPresentOrElse(
                client -> {
                    clientService.resetPassword(client);
                    new ResponseEntity(HttpStatus.OK);
                },
                () -> new ResponseEntity(HttpStatus.BAD_REQUEST));
    }

}
