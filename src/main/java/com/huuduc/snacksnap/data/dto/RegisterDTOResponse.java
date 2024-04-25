package com.huuduc.snacksnap.data.dto;

import com.huuduc.snacksnap.data.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Register DTO Response
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTOResponse {

    private String email;
    private String fullName;
    private Date birthday;
    private boolean status;
    private Role role;
}
