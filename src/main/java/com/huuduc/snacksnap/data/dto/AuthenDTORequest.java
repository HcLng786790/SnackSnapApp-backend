package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication DTO Request
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenDTORequest {

    private String email;
    private String password;
}
