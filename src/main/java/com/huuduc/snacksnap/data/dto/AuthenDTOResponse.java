package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication DTO Response
 *
 * @author huuduc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenDTOResponse {

    private String msg;
    private String token;
    private Long userId;
    private Long roleId;
    private boolean active;
}
