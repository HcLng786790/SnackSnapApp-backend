package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.PromotionDTORequest;
import com.huuduc.snacksnap.service.PromotionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody PromotionDTORequest promotionDTORequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.promotionService.create(promotionDTORequest));
    }
}
