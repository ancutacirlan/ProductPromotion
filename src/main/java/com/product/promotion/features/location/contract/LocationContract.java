package com.product.promotion.features.location.contract;

import com.product.promotion.features.location.Location;
import com.product.promotion.features.location.LocationDto;

public interface LocationContract {

    Location getLocationById(Integer id);
    Location updateLocation(LocationDto dto);

}
