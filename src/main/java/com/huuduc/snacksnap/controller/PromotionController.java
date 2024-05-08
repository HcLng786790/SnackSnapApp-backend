package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.PromotionDTORequest;
import com.huuduc.snacksnap.service.PromotionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody PromotionDTORequest promotionDTORequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.promotionService.create(promotionDTORequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestParam long promotionId,
                                          @RequestParam boolean status){

        return ResponseEntity.ok(this.promotionService.updateStatus(promotionId,status));
    }

    @GetMapping("/get-active")
    public ResponseEntity<?> getActive(){

        return ResponseEntity.ok(this.promotionService.getActive());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-hide")
    public ResponseEntity<?> getHide(){

        return ResponseEntity.ok(this.promotionService.getHide());
    }
}
