package com.product.promotion.features.order;

import org.modelmapper.PropertyMap;


public class Utils {


    private Utils() {
    }

    public static PropertyMap<OrderDto, Order> orderMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().getClientId().setId(source.getClientId());
            map().getLocationId().setId(source.getLocationId());
        }
    };

    public static PropertyMap<Order, OrderDto> orderFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setClientId(source.getClientId().getId());
            map().setLocationId(source.getLocationId().getId());
            map().setClientFistName(source.getClientId().getFirstName());
            map().setClientLastName(source.getClientId().getLastName());
        }
    };

}
