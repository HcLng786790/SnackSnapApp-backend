package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.CartDetailsDTORequest;
import com.huuduc.snacksnap.data.dto.CartDetailsDTOResponse;
import com.huuduc.snacksnap.data.entity.Cart;
import com.huuduc.snacksnap.data.entity.CartDetails;
import com.huuduc.snacksnap.data.entity.Product;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.repository.CartDetailsRepositoy;
import com.huuduc.snacksnap.repository.CartRepository;
import com.huuduc.snacksnap.repository.ProductRepository;
import com.huuduc.snacksnap.service.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CartDetailsServiceImpl implements CartDetailsService {

    @Autowired
    private CartDetailsRepositoy cartDetailsRepositoy;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartDetailsDTOResponse> getAll() {

        List<CartDetails> cartDetails = this.cartDetailsRepositoy.findAll();

        List<CartDetailsDTOResponse> cartDetailsDTORespons = new ArrayList<>();

        for (CartDetails cartDetail : cartDetails){
            CartDetailsDTOResponse cartDetailsDTOResponse = toDTO(cartDetail);
            cartDetailsDTORespons.add(cartDetailsDTOResponse);
        }

        return cartDetailsDTORespons;

    }

    @Override
    public CartDetailsDTOResponse create(long productId) {
        return null;
    }

    @Override
    public List<CartDetailsDTOResponse> findByCart(long id) {

        //Check cart exit (thiếu)

        //Get
        List<CartDetails> cartDetails = this.cartDetailsRepositoy.findByCartId(id);

        List<CartDetailsDTOResponse> cartDetailsDTORespons = new ArrayList<>();

        for (CartDetails cartDetail : cartDetails){
            CartDetailsDTOResponse cartDetailsDTOResponse = toDTO(cartDetail);
            cartDetailsDTORespons.add(cartDetailsDTOResponse);
        }

        return cartDetailsDTORespons;
    }

    @Override
    public CartDetailsDTOResponse addToCart(long cartId, CartDetailsDTORequest cartDetailsDTORequest) {

        //Check exits cart (thieu)


        CartDetailsDTOResponse cartDetailsDTOResponse = new CartDetailsDTOResponse();
        //Get cart from request
        Cart findCart = this.cartRepository.findById(cartId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Cart id",cartId))
        );
        //Get product from request
        Product product = this.productRepository
                .findById(cartDetailsDTORequest.getProductId())
                .orElseThrow(
                        () -> new NotFoundException(Collections.singletonMap("Product id",cartDetailsDTORequest.getProductId()))
                );

        double price = product.getPrice()*cartDetailsDTORequest.getQuantity();

        List<CartDetails> cartDetailsList = findCart.getCartDetailsList();

        boolean found = false;
        for(CartDetails cartDetails:cartDetailsList){
            if(cartDetails.getProduct().getId().equals(product.getId())){

                int slCu = cartDetails.getSoLuong();
                int slMoi = cartDetailsDTORequest.getQuantity()+slCu;
                cartDetails.setSoLuong(slMoi);
                cartDetails.setPriceSP(product.getPrice() * slMoi);
                this.cartDetailsRepositoy.save(cartDetails);
                cartDetailsDTOResponse = toDTO(cartDetails);
                found= true;
                break;
            }
        }

        if(!found){
            CartDetails cartDetails = new CartDetails();
            cartDetails.setSoLuong(cartDetailsDTORequest.getQuantity());
            cartDetails.setPriceSP(product.getPrice()*cartDetailsDTORequest.getQuantity());
            cartDetails.setProduct(product);
            cartDetails.setCart(findCart);
            this.cartDetailsRepositoy.save(cartDetails);
            cartDetailsDTOResponse = toDTO(cartDetails);
        }

        findCart.setTotalPrice(findCart.getTotalPrice()+price);
        this.cartRepository.save(findCart);
        return cartDetailsDTOResponse;
    }

    @Override
    public CartDetailsDTOResponse updateCartDetails(long id, int soluong) {

        CartDetails cartDetails = this.cartDetailsRepositoy.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Cart Details id",id))
        );

        CartDetailsDTOResponse cartDetailsDTOResponse = new CartDetailsDTOResponse();


        Cart cart = this.cartRepository.findById(cartDetails.getCart().getId()).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Cart id",id))
        );

        if(soluong == 0){
            this.cartDetailsRepositoy.deleteById(cartDetails.getId());
            cart.setTotalPrice(updateTotalPrice(cart));
            this.cartRepository.save(cart);
            return cartDetailsDTOResponse;
        }

        cartDetails.setSoLuong(soluong);
        double newPrice = soluong*cartDetails.getProduct().getPrice();
        cartDetails.setPriceSP(newPrice);
        this.cartDetailsRepositoy.save(cartDetails);
        cartDetailsDTOResponse = toDTO(cartDetails);


        cart.setTotalPrice(updateTotalPrice(cart));
        this.cartRepository.save(cart);

        return cartDetailsDTOResponse;
    }

    @Override
    public List<CartDetailsDTOResponse> updateCartDetailsByProduct(long productId) {

        List<CartDetails> cartDetails = this.cartDetailsRepositoy.findByProductIdAndOrdersIdIsNull(productId);

        List<CartDetailsDTOResponse> cartDetailsDTOResponseList = cartDetails.stream().map(
                c -> toDTO(c)
        ).toList();

        return cartDetailsDTOResponseList;
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

    public double updateTotalPrice(Cart cart){

        double totalPrice =0;
        for (CartDetails cartDetails:cart.getCartDetailsList()){
            totalPrice+=cartDetails.getPriceSP();
        }

        return totalPrice;
    }
}
