package com.huuduc.snacksnap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTOResponse {

    private Long id;
    private double discount;
    private boolean status;
}
