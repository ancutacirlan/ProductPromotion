package com.product.promotion.features.producer;


import com.product.promotion.features.notice.contract.NoticeContract;
import org.modelmapper.PropertyMap;



public class Utils {

    public Utils() {
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
            map().setFirstName(source.getClientId().getFirstName());
            map().setLastName(source.getClientId().getLastName());
            map().setEmail(source.getClientId().getEmail());
            map().setPhone(source.getClientId().getPhone());
            map().setCity(source.getClientId().getLocationId().getCity());
            map().setDistrict(source.getClientId().getLocationId().getCity());
            map().setCountry(source.getClientId().getLocationId().getCountry());
            map().setVillage(source.getClientId().getLocationId().getVillage());
            map().setStreet(source.getClientId().getLocationId().getStreet());
            map().setBloc(source.getClientId().getLocationId().getBloc());
            map().setStair(source.getClientId().getLocationId().getStair());
            map().setNo(source.getClientId().getLocationId().getNo());
            map().setValidAccount(source.getClientId().getValidAccount());
            map().setRole(source.getClientId().getRole());
            map().setLocationId(source.getClientId().getLocationId().getId());
        }
    };

}
