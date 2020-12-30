package com.product.promotion.features.client.contract;

import com.product.promotion.features.client.Client;
import com.product.promotion.features.client.ClientDto;
import com.product.promotion.features.authentification.LoginRequest;

public interface ClientContract {

    String authenticate(LoginRequest loginRequest);

    Client getClientById(Integer id);
    ClientDto register(ClientDto clientDto);
    Client getClientByEmail(String email);
    Client updateClient(ClientDto clientDto);
}
