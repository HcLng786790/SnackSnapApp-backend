package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Cart DTO Request
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTOResponse {

    private long id;
    private List<CartDetailsDTOResponse> cartDetailsDTOResponseList;
    private double totalPrice;
}
