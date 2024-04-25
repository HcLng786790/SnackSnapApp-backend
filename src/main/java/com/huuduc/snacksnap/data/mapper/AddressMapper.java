package com.huuduc.snacksnap.data.mapper;

import com.huuduc.snacksnap.data.dto.AddressDTORequest;
import com.huuduc.snacksnap.data.dto.AddressDTOResponse;
import com.huuduc.snacksnap.data.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(ignore = true, target = "user")
    Address toEntity(AddressDTORequest addressDTORequest);

    AddressDTOResponse toDTO(Address address);
}
