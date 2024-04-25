package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cart Controller
 *
 * @author huuduc
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/total/{id}")
    public ResponseEntity<?> getToTalPrice(@PathVariable long id){

        return ResponseEntity.ok(this.cartService.getTotalPrice(id));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){

        return ResponseEntity.ok(this.cartService.getByCart(id));
    }

}
