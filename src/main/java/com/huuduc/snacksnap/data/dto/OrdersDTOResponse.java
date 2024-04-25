package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Orders DTO Response v2
 *
 * @author huuduc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTOResponse {

    private Long id;
    private List<AddressDTOResponse> addressDTOResponses;
    private double totalPrice;
    private int typeDelivery;

    private AddressDTOResponse addressDTOResponse;
}
