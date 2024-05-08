package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.PromotionDTORequest;
import com.huuduc.snacksnap.data.dto.PromotionDTOResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromotionService {


    PromotionDTOResponse create(PromotionDTORequest promotionDTORequest);

    PromotionDTOResponse updateStatus(long promotionId,boolean status);

    List<PromotionDTOResponse> getActive();

    List<PromotionDTOResponse> getHide();
}
