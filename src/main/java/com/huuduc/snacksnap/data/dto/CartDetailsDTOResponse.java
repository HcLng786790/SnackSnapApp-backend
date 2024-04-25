package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CartDetails DTO Response
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailsDTOResponse {

    private Long id;
    private String nameSP;
    private double priceSP;
    private String imgSP;
    private int soluongSP;
}
