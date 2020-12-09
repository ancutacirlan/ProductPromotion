package com.product.promotion.features.notice;

import org.modelmapper.PropertyMap;

public class Utils {


    private Utils() {
    }

    public static PropertyMap<NoticeDto, Notice> noticeMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().getProducerId().setId(source.getProducerId());
            map().getVegetableId().setId(source.getVegetableId());
        }
    };

    public static PropertyMap<Notice, NoticeDto> noticeFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setProducerId(source.getProducerId().getId());
            map().setProducerFirstName(source.getProducerId().getClientId().getFirst_name());
            map().setProducerLastName(source.getProducerId().getClientId().getLast_name());
            map().setVegetableId(source.getVegetableId().getId());
            map().setVegetableName(source.getVegetableId().getName());
        }
    };

}
