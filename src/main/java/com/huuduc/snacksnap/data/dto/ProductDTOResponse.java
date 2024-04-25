package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product DTO Response
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOResponse {
    private long id;
    private String name;
    private double price;
    private int type;
    private boolean favorite;
    private String img;
}
