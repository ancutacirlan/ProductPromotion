package com.product.promotion.features.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    private FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedbackDto> create(@RequestBody FeedbackDto dto) {
        return ResponseEntity.ok(feedbackService.create(dto));
    }

    @GetMapping(path = "/noticeId", produces = MediaType.APPLICATION_JSON_VALUE, params = "noticeId")
    public ResponseEntity<List<FeedbackDto>> getAllByNotice(@RequestParam(value = "noticeId") Integer noticeId) {
        return ResponseEntity.ok(feedbackService.getAllByNotice(noticeId));
    }


}
