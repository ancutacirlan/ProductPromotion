package com.product.promotion.features.notice;

import com.product.promotion.features.notice.contract.NoticeContract;
import com.product.promotion.features.producer.contract.ProducerContract;
import com.product.promotion.features.vegetable.contract.VegetableContract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService implements NoticeContract {

    private NoticeRepository noticeRepository;
    private ModelMapper modelMapper;
    private ProducerContract producerContract;
    private VegetableContract vegetableContract;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, ModelMapper modelMapper,
                         @Lazy ProducerContract producerContract, VegetableContract vegetableContract) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
        this.producerContract = producerContract;
        this.vegetableContract = vegetableContract;
        modelMapper.addMappings(Utils.noticeFieldMapping);
        modelMapper.addMappings(Utils.noticeMapping);
    }

    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    NoticeDto create(@NotNull NoticeDto dto) {
        Notice notice = modelMapper.map(dto,Notice.class);
        notice.setProducerId(producerContract.getProducerById(dto.getProducerId()));
        notice.setVegetableId(vegetableContract.getVegetableById(dto.getVegetableId()));
        return modelMapper.map(noticeRepository.save(notice), NoticeDto.class);
    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */

    List<NoticeDto> getAll() {
        return noticeRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, NoticeDto.class))
                .collect(Collectors.toList());
    }



   public List<NoticeDto> getAllByProducer(@NotNull Integer producerId) {
        return noticeRepository
                .findAllByProducerIdAndIsDeletedFalse(producerContract.getProducerById(producerId))
                .stream()
                .map(item -> modelMapper.map(item, NoticeDto.class))
                .collect(Collectors.toList());
    }



    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    public NoticeDto getById(@NotNull Integer id) {
        return noticeRepository
                .findById(id)
                .map(result -> modelMapper.map(result, NoticeDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Updates an existing entity from the database.
     *
     * @param dto The DTO object which contains all the information for the entity.
     * @return A DTO object updated with the information which has stored in the database.
     */
    public NoticeDto update(@NotNull NoticeDto dto) {
        return noticeRepository
                .findById(dto.getId())
                .map(result -> {
                    Notice toBeUpdated = modelMapper.map(dto, Notice.class);
                    return modelMapper.map(noticeRepository.save(toBeUpdated), NoticeDto.class);
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
        return noticeRepository
                .findById(id)
                .map(result -> {
                    result.setIsDeleted(true);
                    return noticeRepository.save(result).getIsDeleted();
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
    public Notice getNoticeById(Integer id) {
        return noticeRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

}
