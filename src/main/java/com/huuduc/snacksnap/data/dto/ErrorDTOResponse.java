package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error DTO Response
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTOResponse {

    private String msg;
    private Object error;
    private String path;
}
