package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.CartDetailsDTORequest;
import com.huuduc.snacksnap.data.dto.CartDetailsDTOResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartDetailsService {
    List<CartDetailsDTOResponse> getAll();

//    CartDTOResponse updateById(long id, Cart cart);

    CartDetailsDTOResponse create(long productId);

    List<CartDetailsDTOResponse> findByCart(long id);

    CartDetailsDTOResponse addToCart(long cartId, CartDetailsDTORequest cartDetailsDTORequest);

    CartDetailsDTOResponse updateCartDetails(long id,int soluong);

    List<CartDetailsDTOResponse> updateCartDetailsByProduct(long productId);
}
