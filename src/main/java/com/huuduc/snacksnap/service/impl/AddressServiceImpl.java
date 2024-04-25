package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.AddressDTORequest;
import com.huuduc.snacksnap.data.dto.AddressDTOResponse;
import com.huuduc.snacksnap.data.entity.Address;
import com.huuduc.snacksnap.data.entity.User;
import com.huuduc.snacksnap.data.mapper.AddressMapper;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.exception.NullException;
import com.huuduc.snacksnap.repository.AddressRepository;
import com.huuduc.snacksnap.repository.UserReopsitory;
import com.huuduc.snacksnap.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserReopsitory userReopsitory;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressDTOResponse create(long userId, AddressDTORequest addressDTORequest) {

        User findUser = this.userReopsitory.findById(userId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("user id", userId))
        );

        Map<String, Object> errors = new HashMap<>();

        if (ObjectUtils.isEmpty(addressDTORequest.getLocation())) {
            errors.put("Address location:", null);
        }
        if (ObjectUtils.isEmpty(addressDTORequest.getPhoneReceiver())) {
            errors.put("Phone receiver:", null);
        }
        if (ObjectUtils.isEmpty(addressDTORequest.getNameReceiver())) {
            errors.put("Name Receiver:", null);
        }

        if (errors.size() > 0) {
            throw new NullException(Collections.singletonMap("Address Request:", errors));
        }

        Address newAddress = this.addressMapper.toEntity(addressDTORequest);

        //Check if defaults null then set defaults is false
        if (ObjectUtils.isEmpty(addressDTORequest.isDefaults())) {
            newAddress.setDefaults(false);
        }

        newAddress.setUser(findUser);

        Address defaultsAddress = this.addressRepository.findByUserIdAndDefaultsIsTrue(userId);

        if(defaultsAddress!=null){
            if(newAddress.isDefaults()){
                defaultsAddress.setDefaults(false);
                this.addressRepository.save(defaultsAddress);
            }
        }else{
            newAddress.setDefaults(true);
        }

        this.addressRepository.save(newAddress);

        return this.addressMapper.toDTO(newAddress);
    }

    @Override
    public AddressDTOResponse getDefault(long userId) {

        User findUser = this.userReopsitory.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException(Collections.singletonMap("User id", userId))
                );

        Address findAddress = this.addressRepository.findByUserIdAndDefaultsIsTrue(userId);

        if (findAddress == null || ObjectUtils.isEmpty(findAddress)) {
            throw new NotFoundException(Collections.singletonMap("Defaults", userId));
        }

        return this.addressMapper.toDTO(findAddress);
    }

    @Override
    public List<AddressDTOResponse> getAll(long userId) {

        User findUser = this.userReopsitory.findById(userId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("User id", userId))
        );

        List<AddressDTOResponse> addressDTOResponses = this.addressRepository.findByUserId(userId)
                .stream().map(a -> this.addressMapper.toDTO(a)).toList();

        return addressDTOResponses;

    }

    @Override
    public AddressDTOResponse updateById(long id, AddressDTORequest addressDTORequest) {
        //Đang trong quá trình thực hiện
//
//        Address findAddress = this.addressRepository.findById(id).orElseThrow(
//                ()-> new NotFoundException(Collections.singletonMap("Address id",id))
//        );
//
//        if(!ObjectUtils.isEmpty(addressDTORequest.getLocation())){
//            findAddress.set
//        }

        return null;
    }
}
