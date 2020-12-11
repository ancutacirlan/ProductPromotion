package com.product.promotion.features.order.contract;


import com.product.promotion.features.order.Order;
import com.product.promotion.features.order.OrderDto;

import javax.validation.constraints.NotNull;

public interface OrderContract {

    Order getOrderById(Integer id);
    OrderDto update(@NotNull OrderDto orderDto);
    OrderDto getById(@NotNull Integer id);

}
