package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.CartDetailsDTORequest;
import com.huuduc.snacksnap.data.entity.CartDetails;
import com.huuduc.snacksnap.repository.CartDetailsRepositoy;
import com.huuduc.snacksnap.service.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CartDetails Controller
 *
 * @author huuduc
 */
@RestController
@RequestMapping("/cartDetails")
public class CartDetailsController {

    @Autowired
    private CartDetailsService cartDetailsService;
    @Autowired
    private CartDetailsRepositoy cartDetailsRepositoy;


    @GetMapping("/all")
    public ResponseEntity<?> getAll(){

        return ResponseEntity.ok(this.cartDetailsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByCart(@PathVariable long id){

        return ResponseEntity.ok(this.cartDetailsService.findByCart(id));
    }

    @PostMapping("/add/{cartId}")
    public ResponseEntity<?> addToCart(@PathVariable long cartId,
                                       @RequestBody CartDetailsDTORequest cartDetailsDTORequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.cartDetailsService.addToCart(cartId,cartDetailsDTORequest));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartDetails(@RequestParam long cartDetailsId,
                                              @RequestParam int soLuong){

        return ResponseEntity.ok(this.cartDetailsService.updateCartDetails(cartDetailsId,soLuong));
    }

    @GetMapping("/getByProduct/{productId}")
    public ResponseEntity<?> getByProduct(@PathVariable long productId){

        return ResponseEntity.ok(this.cartDetailsService.updateCartDetailsByProduct(productId));
    }

}
