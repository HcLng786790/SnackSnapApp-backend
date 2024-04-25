package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Orders DTO Request
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTORequest{
    private String location;
}
