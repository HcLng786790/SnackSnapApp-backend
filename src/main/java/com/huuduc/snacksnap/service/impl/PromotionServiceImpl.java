package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.PromotionDTORequest;
import com.huuduc.snacksnap.data.dto.PromotionDTOResponse;
import com.huuduc.snacksnap.data.entity.Promotion;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.repository.ProductRepository;
import com.huuduc.snacksnap.repository.PromotionRepository;
import com.huuduc.snacksnap.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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

    @Override
    public PromotionDTOResponse updateStatus(long promotionId,boolean status) {

        Promotion findPromotion = this.promotionRepository.findById(promotionId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Promotion id",promotionId))
        );

        findPromotion.setStatus(status);

        this.promotionRepository.save(findPromotion);

        return toDTO(findPromotion);
    }

    @Override
    public List<PromotionDTOResponse> getActive() {

        List<Promotion> promotions = this.promotionRepository.findByStatusIsTrue();
        List<PromotionDTOResponse> promotionDTOResponses = promotions.stream().map(
                p-> toDTO(p)
        ).toList();

        return promotionDTOResponses;

    }

    @Override
    public List<PromotionDTOResponse> getHide() {
        List<Promotion> promotions = this.promotionRepository.findByStatusIsFalse();
        List<PromotionDTOResponse> promotionDTOResponses = promotions.stream().map(
                p-> toDTO(p)
        ).toList();

        return promotionDTOResponses;
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
        promotionDTOResponse.setStatus(promotion.isStatus());

        return promotionDTOResponse;

    }
}
