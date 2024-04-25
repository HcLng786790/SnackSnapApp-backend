package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User DTO Response
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {

    private Long id;
    private String email;
    private Long cartId;
    private String phoneNumber;
    private String name;
    private String img;
}
