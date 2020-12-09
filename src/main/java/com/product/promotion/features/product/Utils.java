package com.product.promotion.features.product;


import org.modelmapper.PropertyMap;

public class Utils {

    public static PropertyMap<ProductDto, Product> productMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().getNoticeId().setId(source.getNoticeId());
            map().getOrderId().setId(source.getOrderId());
        }
    };

    public static PropertyMap<Product, ProductDto> productFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setOrderId(source.getOrderId().getId());
            map().setNoticeId(source.getNoticeId().getId());
            map().setProducerFirstName(source.getNoticeId().getProducerId().getClientId().getFirst_name());
            map().setProducerLastName(source.getNoticeId().getProducerId().getClientId().getLast_name());
            map().setVegetableName(source.getNoticeId().getVegetableId().getName());
        }
    };


}
