package com.product.promotion.features.feedback;

import com.product.promotion.features.client.contract.ClientContract;
import com.product.promotion.features.notice.NoticeDto;
import com.product.promotion.features.notice.contract.NoticeContract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FeedbackService {

    private FeedbackRepository feedbackRepository;
    private ModelMapper modelMapper;
    private ClientContract clientContract;
    private NoticeContract noticeContract;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, ModelMapper modelMapper,
                           ClientContract clientContract, NoticeContract noticeContract) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.clientContract = clientContract;
        this.noticeContract = noticeContract;
        modelMapper.addMappings(Utils.feedbackMapping);
        modelMapper.addMappings(Utils.feedbackFieldMapping);
    }

    /**
     * Creates a new entity in the database.
     *
     * @param dto The DTO required to create the new entity.
     * @return The DTO with the details of the newly created entity.
     */
    FeedbackDto create(@NotNull FeedbackDto dto) {
        Feedback feedback = modelMapper.map(dto, Feedback.class);
        feedback.setClientId(clientContract.getClientById(dto.getClientId()));
        feedback.setNoticeId(noticeContract.getNoticeById(dto.getNoticeId()));
        feedback.setFeedbackId(new FeedbackId(dto.getClientId(), dto.getNoticeId()));
        return modelMapper.map(feedbackRepository.save(feedback), FeedbackDto.class);
    }

    List<FeedbackDto> getAllByNotice(@NotNull Integer noticeId) {
        return feedbackRepository
                .findAllByNoticeIdAndIsDeletedFalse(noticeContract.getNoticeById(noticeId))
                .stream()
                .map(item -> modelMapper.map(item, FeedbackDto.class))
                .collect(Collectors.toList());
    }

}
