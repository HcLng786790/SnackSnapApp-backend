package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.AddressDTORequest;
import com.huuduc.snacksnap.data.dto.AddressDTOResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    AddressDTOResponse create(long userId, AddressDTORequest addressDTORequest);

    AddressDTOResponse getDefault(long userId);

    List<AddressDTOResponse> getAll(long userId);

    AddressDTOResponse updateById(long id, AddressDTORequest addressDTORequest);
}
