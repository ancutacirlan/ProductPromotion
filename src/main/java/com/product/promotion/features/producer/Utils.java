package com.product.promotion.features.producers;

import org.modelmapper.PropertyMap;

public class Utils {

    private Utils() {
    }

    public static PropertyMap<ProducerDto, Producer> producerMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().getClientId().setId(source.getClientId());
        }
    };

    public static PropertyMap<Producer, ProducerDto> producerFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setClientId(source.getClientId().getId());
            map().setFirstName(source.getClientId().getFirst_name());
            map().setLastName(source.getClientId().getLast_name());
            map().setEmail(source.getClientId().getEmail());
            map().setPhone(source.getClientId().getPhone());
        }
    };

}
