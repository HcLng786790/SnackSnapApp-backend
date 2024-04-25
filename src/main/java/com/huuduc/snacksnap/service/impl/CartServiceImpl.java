package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.CartDTOResponse;
import com.huuduc.snacksnap.data.dto.CartDetailsDTOResponse;
import com.huuduc.snacksnap.data.entity.Cart;
import com.huuduc.snacksnap.data.entity.CartDetails;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.repository.CartDetailsRepositoy;
import com.huuduc.snacksnap.repository.CartRepository;
import com.huuduc.snacksnap.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDetailsRepositoy cartDetailsRepositoy;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public double getTotalPrice(long id) {

        List<CartDetails> cartDetailsList = this.cartDetailsRepositoy.findByCartId(id);
        double total=0;
        for (CartDetails cartDetails:cartDetailsList){
            total+=cartDetails.getPriceSP();
        }

        return total;
    }

    @Override
    public CartDTOResponse getByCart(long id) {

        Cart findCart = this.cartRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Cart id",id))
        );

        List<CartDetailsDTOResponse> cartDetailsDTOResponseList = findCart.getCartDetailsList()
                .stream().map(c ->toDTO(c)).toList();

        CartDTOResponse cartDTOResponse = new CartDTOResponse();
        cartDTOResponse.setId(findCart.getId());
        cartDTOResponse.setCartDetailsDTOResponseList(cartDetailsDTOResponseList);
        cartDTOResponse.setTotalPrice(findCart.getTotalPrice());

        return cartDTOResponse;

    }

    public CartDetailsDTOResponse toDTO(CartDetails cartDetails){

        CartDetailsDTOResponse cartDetailsDTOResponse = new CartDetailsDTOResponse();

        cartDetailsDTOResponse.setId(cartDetails.getId());
        cartDetailsDTOResponse.setNameSP(cartDetails.getProduct().getName());
        cartDetailsDTOResponse.setImgSP(cartDetails.getProduct().getImg().getName());
        cartDetailsDTOResponse.setPriceSP(cartDetails.getPriceSP());
        cartDetailsDTOResponse.setSoluongSP(cartDetails.getSoLuong());

        return cartDetailsDTOResponse;

    }
}
