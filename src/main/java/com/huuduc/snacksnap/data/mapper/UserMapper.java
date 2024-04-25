package com.huuduc.snacksnap.data.mapper;

import com.huuduc.snacksnap.data.dto.RegisterDTORequest;
import com.huuduc.snacksnap.data.dto.RegisterDTOResponse;
import com.huuduc.snacksnap.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterDTORequest registerDTORequest);

    RegisterDTOResponse toDTO(User user);
}
