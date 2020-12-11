package com.product.promotion.features.location;

import com.product.promotion.features.location.contract.LocationContract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService implements LocationContract {

    private LocationRepository locationRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    LocationDto create(@NotNull LocationDto dto) {
        Location location = modelMapper.map(dto,Location.class);
        return modelMapper.map(locationRepository.save(location), LocationDto.class);
    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */

    List<LocationDto> getAll() {
        return locationRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, LocationDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    LocationDto getById(@NotNull Integer id) {
        return locationRepository
                .findById(id)
                .map(result -> modelMapper.map(result, LocationDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Updates an existing entity from the database.
     *
     * @param dto The DTO object which contains all the information for the entity.
     * @return A DTO object updated with the information which has stored in the database.
     */
    LocationDto update(@NotNull LocationDto dto) {
        return locationRepository
                .findById(dto.getId())
                .map(result -> {
                    Location toBeUpdated = modelMapper.map(dto, Location.class);
                    return modelMapper.map(locationRepository.save(toBeUpdated), LocationDto.class);
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
        return locationRepository
                .findById(id)
                .map(result -> {
                    result.setIsDeleted(true);
                    return locationRepository.save(result).getIsDeleted();
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
    public Location getLocationById(Integer id) {
        return locationRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
