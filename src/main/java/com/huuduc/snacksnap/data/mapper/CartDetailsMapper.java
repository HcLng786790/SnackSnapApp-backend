package com.huuduc.snacksnap.data.mapper;

import com.huuduc.snacksnap.data.dto.CartDetailsDTOResponse;
import com.huuduc.snacksnap.data.entity.CartDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartDetailsMapper {

    @Mapping(source = "soLuong", target = "soluongSP")
    CartDetailsDTOResponse toDTO(CartDetails cartDetails);
}
