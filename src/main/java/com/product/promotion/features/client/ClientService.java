package com.product.promotion.features.client;

import com.product.promotion.configuration.security.JwtUtils;
import com.product.promotion.features.authentification.LoginRequest;
import com.product.promotion.features.client.contract.ClientContract;
import com.product.promotion.features.location.Location;
import com.product.promotion.features.location.LocationDto;
import com.product.promotion.features.location.LocationRepository;
import com.product.promotion.features.location.contract.LocationContract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements ClientContract {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private ClientRepository clientRepository;
    private LocationRepository locationRepository;
    private ModelMapper modelMapper;
    private LocationContract locationContract;
    private final PasswordEncoder encoder;

    @Autowired
    public ClientService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, LocationRepository locationRepository,
                         ClientRepository clientRepository, ModelMapper modelMapper, LocationContract locationContract, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.locationRepository = locationRepository;
        this.jwtUtils = jwtUtils;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.locationContract = locationContract;
        this.encoder = encoder;
        modelMapper.addMappings(Utils.clientFieldMapping);
        modelMapper.addMappings(Utils.clientMapping);
    }

    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    @Override
    public ClientDto register(@NotNull ClientDto dto) {
        Location location = modelMapper.map(dto, Location.class);
        modelMapper.map(locationRepository.save(location), LocationDto.class);
        Client client = modelMapper.map(dto,Client.class);
        client.setLocationId(location);
        client.setPassword(encoder.encode(dto.getPassword()));
        return modelMapper.map(clientRepository.save(client), ClientDto.class);
    }

    @Override
    public String authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return jwt;
    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */

    List<ClientDto> getAll() {
        return clientRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, ClientDto.class))
                .collect(Collectors.toList());
    }

    List<ClientDto> getAllByRole(@NotNull String role) {
        return clientRepository
                .findAllByRoleAndIsDeletedFalse(role)
                .stream()
                .map(item -> modelMapper.map(item, ClientDto.class))
                .collect(Collectors.toList());
    }
    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    ClientDto getById(@NotNull Integer id) {
        return clientRepository
                .findById(id)
                .map(result -> modelMapper.map(result, ClientDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param email The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    ClientDto getByEmail(@NotNull String email) {
        return clientRepository
                .findByEmailAndIsDeletedFalse(email)
                .map(result -> modelMapper.map(result, ClientDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }


    /**
     * Updates an existing entity from the database.
     *
     * @param dto The DTO object which contains all the information for the entity.
     * @return A DTO object updated with the information which has stored in the database.
     */
    ClientDto update(@NotNull ClientDto dto) {
        return clientRepository
                .findById(dto.getId())
                .map(result -> {
                    Client toBeUpdated = modelMapper.map(dto, Client.class);
                    return modelMapper.map(clientRepository.save(toBeUpdated), ClientDto.class);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Soft-deletes an existing entity from the database.
     *
     * @param id The ID of the entity stored in the database.
     * @return TRUE if the entity was soft-deleted, FALSE otherwise.
     */
    Boolean delete(@NotNull Integer id) {
        return clientRepository
                .findById(id)
                .map(result -> {
                    result.setIsDeleted(true);
                    return clientRepository.save(result).getIsDeleted();
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    @Override
    public Client getClientById(Integer id) {
        return clientRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository
                .findByEmailAndIsDeletedFalse(email)
                .orElseThrow(EntityNotFoundException::new);
    }



}
