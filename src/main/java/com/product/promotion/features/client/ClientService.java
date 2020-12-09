package com.product.promotion.features.client;

import com.product.promotion.features.client.contract.ClientContract;
import com.product.promotion.features.location.contract.LocationContract;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements ClientContract {

    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private LocationContract locationContract;

    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper, LocationContract locationContract) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.locationContract = locationContract;
        modelMapper.addMappings(Utils.clientFieldMapping);
        modelMapper.addMappings(Utils.clientMapping);
    }

    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    ClientDto create(@NotNull ClientDto dto) {
        Client client = modelMapper.map(dto,Client.class);
        client.setLocation_id(locationContract.getLocationById(dto.getLocation_id()));
        return modelMapper.map(clientRepository.save(client), ClientDto.class);
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
}
