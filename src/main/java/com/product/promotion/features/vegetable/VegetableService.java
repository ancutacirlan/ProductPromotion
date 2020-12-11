package com.product.promotion.features.vegetable;

import com.product.promotion.features.vegetable.contract.VegetableContract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VegetableService implements VegetableContract {

    private VegetableRepository vegetableRepository;
    private ModelMapper modelMapper;

    @Autowired
    public VegetableService(VegetableRepository vegetableRepository, ModelMapper modelMapper) {
        this.vegetableRepository = vegetableRepository;
        this.modelMapper = modelMapper;
    }


    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    VegetableDto create(@NotNull VegetableDto dto){
        Vegetable vegetable = modelMapper.map(dto,Vegetable.class);
        return modelMapper.map(vegetableRepository.save(vegetable),VegetableDto.class);
    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */

    List<VegetableDto> getAll() {
        return vegetableRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, VegetableDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    VegetableDto getById(@NotNull Integer id) {
        return vegetableRepository
                .findById(id)
                .map(result -> modelMapper.map(result, VegetableDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Gets an entity from the database based on its name.
     *
     * @param name The NAME of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    VegetableDto getByName(@NotNull String name) {
        return vegetableRepository
                .findByNameAndIsDeletedFalse(name)
                .map(result -> modelMapper.map(result, VegetableDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Updates an existing entity from the database.
     *
     * @param dto The DTO object which contains all the information for the entity.
     * @return A DTO object updated with the information which has stored in the database.
     */
    VegetableDto update(@NotNull VegetableDto dto) {
        return vegetableRepository
                .findById(dto.getId())
                .map(result -> modelMapper.map(dto, Vegetable.class))
                .map(toBeUpdated -> modelMapper.map(vegetableRepository.save(toBeUpdated), VegetableDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Soft-deletes an existing entity from the database.
     *
     * @param id The ID of the entity stored in the database.
     * @return TRUE if the entity was soft-deleted, FALSE otherwise.
     */
    Boolean delete(@NotNull Integer id) {
        return vegetableRepository
                .findById(id)
                .map(result -> {
                    result.setIsDeleted(true);
                    return vegetableRepository.save(result).getIsDeleted();
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Vegetable getVegetableById(Integer id) {
        return vegetableRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
