package com.product.promotion.features.product.contract;

import com.product.promotion.features.product.ProductDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProductsContract {
    List<ProductDto> getAllByOrder(@NotNull Integer orderId);

}
