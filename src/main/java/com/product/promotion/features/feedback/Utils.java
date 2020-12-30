package com.product.promotion.features.feedback;


import org.modelmapper.PropertyMap;

public class Utils {

    public static PropertyMap<FeedbackDto, Feedback> feedbackMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().getNoticeId().setId(source.getNoticeId());
            map().getClientId().setId(source.getClientId());
        }
    };

    public static PropertyMap<Feedback, FeedbackDto> feedbackFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setNoticeId(source.getNoticeId().getId());
            map().setClientId(source.getClientId().getId());
        }
    };

}
