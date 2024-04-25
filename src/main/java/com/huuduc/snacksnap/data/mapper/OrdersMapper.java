package com.huuduc.snacksnap.data.mapper;

import com.huuduc.snacksnap.data.dto.OrdersDTOResponse;
import com.huuduc.snacksnap.data.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface OrdersMapper {

    OrdersDTOResponse toDTO(Orders orders);
}
