package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.PromotionDTORequest;
import com.huuduc.snacksnap.data.dto.PromotionDTOResponse;
import org.springframework.stereotype.Service;

@Service
public interface PromotionService {


    PromotionDTOResponse create(PromotionDTORequest promotionDTORequest);
}
