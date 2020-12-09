package com.product.promotion.features.order;

import com.product.promotion.features.client.contract.ClientContract;
import com.product.promotion.features.location.contract.LocationContract;
import com.product.promotion.features.order.contract.OrderContract;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderContract {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private ClientContract clientContract;
    private LocationContract locationContract;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, ClientContract clientContract, LocationContract locationContract) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.clientContract = clientContract;
        this.locationContract = locationContract;
    }


    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    OrderDto create(@NotNull OrderDto dto) {
        Order order = modelMapper.map(dto, Order.class);
        order.setClientId(clientContract.getClientById(dto.getClientId()));
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */

    List<OrderDto> getAll() {
        return orderRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, OrderDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    OrderDto getById(@NotNull Integer id) {
        return orderRepository
                .findById(id)
                .map(result -> modelMapper.map(result, OrderDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Updates an existing entity from the database.
     *
     * @param dto The DTO object which contains all the information for the entity.
     * @return A DTO object updated with the information which has stored in the database.
     */
    OrderDto update(@NotNull OrderDto dto) {
        return orderRepository
                .findById(dto.getId())
                .map(result -> {
                    Order toBeUpdated = modelMapper.map(dto, Order.class);
                    return modelMapper.map(orderRepository.save(toBeUpdated), OrderDto.class);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Soft-deletes an existing entity from the database.
     *
     * @param id The ID of the entity stored in the database.
     * @return TRUE if the entity was soft-deleted, FALSE otherwise.
     */
    Boolean delete(@NotNull Integer id) {
        return orderRepository
                .findById(id)
                .map(result -> {
                    result.setIsDeleted(true);
                    return orderRepository.save(result).getIsDeleted();
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    @Override
    public Order getOrderById(Integer id) {
        return orderRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

}
