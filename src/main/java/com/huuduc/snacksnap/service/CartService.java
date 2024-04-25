package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.CartDTOResponse;
import com.huuduc.snacksnap.data.entity.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    double getTotalPrice(long id);

    CartDTOResponse getByCart(long id);
}
