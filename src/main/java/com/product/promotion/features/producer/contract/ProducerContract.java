package com.product.promotion.features.producer.contract;

import com.product.promotion.features.producer.Producer;
import com.product.promotion.features.producer.ProducerDto;

public interface ProducerContract {

    Producer getProducerById(Integer id);
    ProducerDto register(ProducerDto producerDto);

}
