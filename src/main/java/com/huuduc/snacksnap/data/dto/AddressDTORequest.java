package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Address DTO Request
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTORequest {

    private String location;
    private String nameReceiver;
    private String phoneReceiver;
    private boolean defaults;
}
