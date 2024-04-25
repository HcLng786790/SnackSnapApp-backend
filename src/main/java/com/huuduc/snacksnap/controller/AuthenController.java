package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.AuthenDTORequest;
import com.huuduc.snacksnap.service.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auhthentication Controller
 *
 * @author huuduc
 */
@RestController
public class AuthenController {

    @Autowired
    private AuthenService authenService;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody(required = false) AuthenDTORequest authenDTORequest){

        return ResponseEntity.ok(this.authenService.login(authenDTORequest));

    }
}
