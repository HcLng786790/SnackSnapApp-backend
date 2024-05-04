package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.PromotionDTORequest;
import com.huuduc.snacksnap.data.dto.PromotionDTOResponse;
import com.huuduc.snacksnap.data.entity.Promotion;
import com.huuduc.snacksnap.repository.ProductRepository;
import com.huuduc.snacksnap.repository.PromotionRepository;
import com.huuduc.snacksnap.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public PromotionDTOResponse create(PromotionDTORequest promotionDTORequest) {

        Promotion newPromotion = toEntity(promotionDTORequest);

        this.promotionRepository.save(newPromotion);

        return toDTO(newPromotion);
    }

    public Promotion toEntity(PromotionDTORequest promotionDTORequest){

        Promotion promotion = new Promotion();
        promotion.setDiscount(promotionDTORequest.getDiscount());
        promotion.setStatus(promotionDTORequest.isStatus());

        return promotion;
    }

    public PromotionDTOResponse toDTO(Promotion promotion){
        PromotionDTOResponse promotionDTOResponse = new PromotionDTOResponse();
        promotionDTOResponse.setId(promotion.getId());
        promotionDTOResponse.setDiscount(promotion.getDiscount());
        promotion.setStatus(promotion.isStatus());

        return promotionDTOResponse;

    }
}
