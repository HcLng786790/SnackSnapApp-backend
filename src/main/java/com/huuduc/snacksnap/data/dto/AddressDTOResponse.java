package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Address DTO Response
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTOResponse {

    private Long id;
    private String location;
    private String nameReceiver;
    private String phoneReceiver;
    private boolean defaults;
}
