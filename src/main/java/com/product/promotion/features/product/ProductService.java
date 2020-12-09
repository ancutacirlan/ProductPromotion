package com.product.promotion.features.product;


import com.product.promotion.features.notice.contract.NoticeContract;
import com.product.promotion.features.order.contract.OrderContract;
import com.product.promotion.features.producer.ProducerDto;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private NoticeContract noticeContract;
    private OrderContract orderContract;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper,
                          NoticeContract noticeContract, OrderContract orderContract) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.noticeContract = noticeContract;
        this.orderContract = orderContract;
        modelMapper.addMappings(Utils.productFieldMapping);
        modelMapper.addMappings(Utils.productMapping);
    }

    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    ProductDto create(@NotNull ProductDto dto){
        Product product = modelMapper.map(dto,Product.class);
        product.setOrderId(orderContract.getOrderById(dto.getOrderId()));
        product.setNoticeId(noticeContract.getNoticeById(dto.getNoticeId()));
        return modelMapper.map(productRepository.save(product),ProductDto.class);
    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */

    List<ProductDto> getAll() {
        return productRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, ProductDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    ProducerDto getById(@NotNull Integer id) {
        return productRepository
                .findById(id)
                .map(result -> modelMapper.map(result, ProducerDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param orderId The order ID of the entity stored in the database.
     * @return A list of  DTO object which contains information about the requested entity.
     */
    List<ProductDto> getAllByOrder(@NotNull Integer orderId) {
        return productRepository
                .getAllByOrderIdAndIsDeletedFalse(orderId)
                .stream()
                .map(item -> modelMapper.map(item, ProductDto.class))
                .collect(Collectors.toList());
    }


}
