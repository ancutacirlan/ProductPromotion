package com.product.promotion.features.producer;

import com.product.promotion.features.client.Client;
import com.product.promotion.features.client.ClientDto;
import com.product.promotion.features.client.ClientRepository;
import com.product.promotion.features.client.contract.ClientContract;
import com.product.promotion.features.location.Location;
import com.product.promotion.features.location.LocationDto;
import com.product.promotion.features.location.LocationRepository;
import com.product.promotion.features.notice.contract.NoticeContract;
import com.product.promotion.features.producer.contract.ProducerContract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProducerService implements ProducerContract {

    private ProducerRepository producerRepository;
    private ModelMapper modelMapper;
    private ClientContract clientContract;
    private ClientRepository clientRepository;
    private LocationRepository locationRepository;
    private final PasswordEncoder encoder;
    private NoticeContract noticeContract;


    @Autowired
    public ProducerService(ProducerRepository producerRepository, ModelMapper modelMapper,
                           ClientContract clientContract, ClientRepository clientRepository,
                           LocationRepository locationRepository, PasswordEncoder encoder, NoticeContract noticeContract) {
        this.producerRepository = producerRepository;
        this.modelMapper = modelMapper;
        this.noticeContract = noticeContract;
        modelMapper.addMappings(Utils.producerFieldMapping);
        modelMapper.addMappings(Utils.producerMapping);
        this.clientContract = clientContract;
        this.clientRepository = clientRepository;
        this.locationRepository = locationRepository;
        this.encoder = encoder;
    }


    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    @Override
    public ProducerDto register(@NotNull ProducerDto dto){
        Location location = modelMapper.map(dto, Location.class);
        modelMapper.map(locationRepository.save(location), LocationDto.class);
        Client client = modelMapper.map(dto,Client.class);
        client.setLocationId(location);
        client.setPassword(encoder.encode(dto.getPassword()));
        modelMapper.map(clientRepository.save(client), ClientDto.class);
        Producer producer = modelMapper.map(dto,Producer.class);
        producer.setClientId(client);
        return modelMapper.map(producerRepository.save(producer),ProducerDto.class);
    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */

    List<ProducerDto> getAll() {
        return producerRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, ProducerDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all active producers entities from the database.
     *
     * @return A list of DTO objects with details of the active producers entitie stored in the database.
     */

    List<ProducerDto> getAllActiveProducer() {
        return producerRepository
                .findAllByActiveIsTrueAndIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, ProducerDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    ProducerDto getById(@NotNull Integer id) {
        return producerRepository
                .findById(id)
                .map(result -> modelMapper.map(result, ProducerDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Updates an existing entity from the database.
     *
     * @param dto The DTO object which contains all the information for the entity.
     * @return A DTO object updated with the information which has stored in the database.
     */
    ProducerDto update(@NotNull ProducerDto dto) {
        return producerRepository
                .findById(dto.getId())
                .map(result -> {
                  locationRepository
                            .findById(dto.getLocationId())
                            .map(loc -> {
                                System.out.println(dto.getLocationId());
                                Location toBeUpdated = modelMapper.map(dto, Location.class);
                                return modelMapper.map(locationRepository.save(toBeUpdated), LocationDto.class);
                            })
                            .orElseThrow(EntityNotFoundException::new);
                    clientRepository
                            .findById(dto.getClientId())
                            .map(cli -> {
                                System.out.println(dto.getClientId());
                                Client toBeUpdated = modelMapper.map(dto, Client.class);
                                toBeUpdated.setLocationId(modelMapper.map(dto, Location.class));
                                return modelMapper.map(clientRepository.save(toBeUpdated), ClientDto.class);
                            })
                            .orElseThrow(EntityNotFoundException::new);
                    Producer toBeUpdated = modelMapper.map(dto, Producer.class);
                    toBeUpdated.setClientId(modelMapper.map(dto, Client.class));
                    return modelMapper.map(producerRepository.save(toBeUpdated), ProducerDto.class);
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
        return producerRepository
                .findById(id)
                .map(result -> {
                    result.setIsDeleted(true);
                    return producerRepository.save(result).getIsDeleted();
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
    public Producer getProducerById(Integer id) {
        return producerRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

}
