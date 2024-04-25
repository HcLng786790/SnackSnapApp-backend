package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.AddressDTORequest;
import com.huuduc.snacksnap.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Address Controller
 *
 * @author huuduc
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> create(@PathVariable long userId,
                                    @RequestBody AddressDTORequest addressDTORequest){
        return ResponseEntity.ok(this.addressService.create(userId,addressDTORequest));
    }

    @GetMapping("/defaults/{userId}")
    public ResponseEntity<?> getAddressDefault(@PathVariable long userId){
        return ResponseEntity.ok(this.addressService.getDefault(userId));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAll(@PathVariable long userId){

        return ResponseEntity.ok(this.addressService.getAll(userId));
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<?> updateById(@PathVariable long id,
                                        @RequestBody AddressDTORequest addressDTORequest){

        return ResponseEntity.ok(this.addressService.updateById(id,addressDTORequest));
    }
}
