package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.ChangePasswordDTORequest;
import com.huuduc.snacksnap.data.dto.RegisterDTORequest;
import com.huuduc.snacksnap.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * User Controller
 *
 * @author huuduc
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getByCartId(@PathVariable long cartId) {

        return ResponseEntity.ok(this.userService.getByCart(cartId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody(required = false) RegisterDTORequest registerDTORequest) {

        return ResponseEntity.ok(this.userService.register(registerDTORequest));
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String email,
                                    @RequestParam String otp) {


        return ResponseEntity.ok(this.userService.verify(email, otp));

    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {

        return ResponseEntity.ok(this.userService.getById(id));
    }


    @Transactional
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateById(@PathVariable long id,
                                        @RequestPart RegisterDTORequest registerDTORequest,
                                        @RequestPart(required = false, name = "fileAvatar") MultipartFile fileAvatar) throws IOException {

        //Check null fileAvater
        if (fileAvatar == null || fileAvatar.isEmpty()) {
            return ResponseEntity.ok(this.userService.updateById(id, registerDTORequest, null));
        }

        //Transfer Multipart file to file
        File tempFile = File.createTempFile("temp", null);
        fileAvatar.transferTo(tempFile);

        return ResponseEntity.ok(this.userService.updateById(id, registerDTORequest, tempFile));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/change-password-by-token")
    public ResponseEntity<?> changePasswordByToken(@RequestBody ChangePasswordDTORequest changePasswordDTORequest) {

        return ResponseEntity.ok(this.userService.changePasswordByToken(changePasswordDTORequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(this.userService.getAll());
    }
}
