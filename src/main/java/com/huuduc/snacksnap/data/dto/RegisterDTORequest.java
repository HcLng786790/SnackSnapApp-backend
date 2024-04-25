package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Register DTO Request
 *
 * @author huuduc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTORequest {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
}
