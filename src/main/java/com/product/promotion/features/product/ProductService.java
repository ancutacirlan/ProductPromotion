package com.product.promotion.features.product;


import com.product.promotion.features.notice.NoticeDto;
import com.product.promotion.features.notice.NoticeService;
import com.product.promotion.features.notice.contract.NoticeContract;
import com.product.promotion.features.order.Order;
import com.product.promotion.features.order.OrderDto;
import com.product.promotion.features.order.OrderService;
import com.product.promotion.features.order.contract.OrderContract;
import com.product.promotion.features.producer.ProducerDto;
import com.product.promotion.features.product.contract.ProductsContract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductsContract {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private NoticeContract noticeContract;
    private OrderContract orderContract;
    private OrderService orderService;
    private NoticeService noticeService;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper,
                          NoticeContract noticeContract, @Lazy OrderContract orderContract, @Lazy OrderService orderService, NoticeService noticeService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.noticeContract = noticeContract;
        this.orderContract = orderContract;
        this.orderService = orderService;
        this.noticeService = noticeService;
        modelMapper.addMappings(Utils.productFieldMapping);
        modelMapper.addMappings(Utils.productMapping);
    }

    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    ResponseEntity<?> create(@NotNull ProductDto dto){
        Product product = modelMapper.map(dto,Product.class);
        product.setOrderId(orderContract.getOrderById(dto.getOrderId()));
        product.setNoticeId(noticeContract.getNoticeById(dto.getNoticeId()));
        NoticeDto notice = noticeService.getById(product.getNoticeId().getId());
        if(notice.getAvaibleQuantity().compareTo(product.getQuantity())>0){
            notice.setAvaibleQuantity(notice.getAvaibleQuantity()- product.getQuantity());
            noticeService.update(notice);
            OrderDto order = orderService.getById(product.getOrderId().getId());
            order.setTotalPrice(order.getTotalPrice()+(product.getNoticeId().getPricePerUnit()*product.getQuantity()));
            orderService.update(order);
           return ResponseEntity.ok(modelMapper.map(productRepository.save(product),ProductDto.class));
        }
        else
            return ResponseEntity.badRequest().body("Quantity not avaible");

    }

    /**
     * Soft-deletes an existing entity from the database.
     *
     * @param id The ID of the entity stored in the database.
     * @return TRUE if the entity was soft-deleted, FALSE otherwise.
     */

    Boolean delete(@NotNull Integer id) {
        return productRepository
                .findById(id)
                .map(result -> {
                    var product = productRepository.findById(id);
                    NoticeDto notice = noticeService.getById(product.get().getNoticeId().getId());

                    notice.setAvaibleQuantity(notice.getAvaibleQuantity()+ product.get().getQuantity());
                    noticeService.update(notice);
                    OrderDto order = orderService.getById(product.get().getOrderId().getId());
                    order.setTotalPrice(order.getTotalPrice()-(product.get().getNoticeId().getPricePerUnit()*product.get().getQuantity()));
                    orderService.update(order);
                    result.setIsDeleted(true);
                    return productRepository.save(result).getIsDeleted();
                })
                .orElseThrow(EntityNotFoundException::new);
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
    @Override
    public List<ProductDto> getAllByOrder(@NotNull Integer orderId) {
        Order order = orderContract.getOrderById(orderId);
        return productRepository
                .findAllByOrderIdAndIsDeletedFalse(order)
                .stream()
                .map(item -> modelMapper.map(item, ProductDto.class))
                .collect(Collectors.toList());
    }


}
