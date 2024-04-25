package com.huuduc.snacksnap.data.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Orders DTO Response
 *
 * @author huuduc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTORes {

    private Long id;

    private double totalPrice;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    private String status;

    private AddressDTOResponse addressDTOResponse;

    private List<CartDetailsDTOResponse> cartDetailsDTOResponseList;
}
