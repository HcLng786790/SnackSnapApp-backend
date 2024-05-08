package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.*;
import com.huuduc.snacksnap.data.entity.*;
import com.huuduc.snacksnap.data.mapper.AddressMapper;
import com.huuduc.snacksnap.enu.StatusConst;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.repository.*;
import com.huuduc.snacksnap.service.MailService;
import com.huuduc.snacksnap.service.OrdersService;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private UserReopsitory userReopsitory;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CartDetailsRepositoy cartDetailsRepositoy;
    @Autowired
    private MailService mailService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public OrdersDTOResponse order(long userId, int type, long addressId) {

        User findUser = this.userReopsitory.findById(userId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("User id", userId))
        );

        Orders newOrders = new Orders();
        newOrders.setOrderDate(new Date());
        newOrders.setUser(findUser);
        newOrders.setTypeDelivery(type);
        if (type == 1) {
            newOrders.setTotalPrice(findUser.getCart().getTotalPrice() + 25);
        } else if (type == 2) {
            newOrders.setTotalPrice(findUser.getCart().getTotalPrice());
        }

        Status findStatus = this.statusRepository.findById(Long.valueOf(1)).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Status", 1))
        );

        Address findAddress = this.addressRepository.findById(addressId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Address id", addressId))
        );

        newOrders.setAddress(findAddress);

        newOrders.setStatus(findStatus);

        this.ordersRepository.save(newOrders);

//        return toDTO(newOrders);
        return null;
    }

    @Override
    public OrdersDTORes order2(long userId, int type, long addressId) {

        Orders newOrders = new Orders();

        User findUser = this.userReopsitory.findById(userId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("User id", userId))
        );

        newOrders.setOrderDate(new Date());
        newOrders.setUser(findUser);
        newOrders.setTypeDelivery(type);

        if (type == 1) {
            newOrders.setTotalPrice(findUser.getCart().getTotalPrice() + 25);
        } else if (type == 2) {
            newOrders.setTotalPrice(findUser.getCart().getTotalPrice());
        }

        Status findStatus = this.statusRepository.findById((long) StatusConst.CONFTRM).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Status", StatusConst.CONFTRM))
        );

        newOrders.setStatus(findStatus);

        Address findAddress = this.addressRepository.findById(addressId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("address id", addressId))
        );

        newOrders.setAddress(findAddress);

        this.ordersRepository.save(newOrders);


        List<CartDetails> cartDetailsList = findUser.getCart().getCartDetailsList();

        for (int i = 0; i < cartDetailsList.size(); i++) {
            cartDetailsList.get(i).setOrders(newOrders);
            this.cartDetailsRepositoy.save(cartDetailsList.get(i));
        }

        // Xóa các cartDetails đã lưu từ cart
//        findUser.getCart().getCartDetailsList().clear();
////            findUser.getCart().setTotalPrice(0);
//        this.cartRepository.save(findUser.getCart());

        // Xóa các CartDetails
        for (CartDetails cartDetails : cartDetailsList) {
            cartDetails.setCart(null);
//            this.cartDetailsRepositoy.delete(cartDetails);
            this.cartDetailsRepositoy.save(cartDetails);
        }

        findUser.getCart().setTotalPrice(0);
        this.cartRepository.save(findUser.getCart());

        return toDTO(newOrders);
    }

    @Override
    public OrdersDTORes order3(long userId, int type, long addressId, long promotionId) {

        Orders newOrders = new Orders();

        User findUser = this.userReopsitory.findById(userId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("User id", userId))
        );

        newOrders.setOrderDate(new Date());
        newOrders.setUser(findUser);
        newOrders.setTypeDelivery(type);

        Promotion findPromotion = this.promotionRepository.findById(promotionId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Promotion id",promotionId))
        );

        newOrders.setPromotion(findPromotion);

        if(findUser.getCart().getTotalPrice() > findPromotion.getDiscount()){
            newOrders.setTotalPrice(findUser.getCart().getTotalPrice() - findPromotion.getDiscount());
        }else{
            newOrders.setTotalPrice(0);
        }

        if (type == 1) {
            newOrders.setTotalPrice(newOrders.getTotalPrice() + 25);
        } else if (type == 2) {
            newOrders.setTotalPrice(newOrders.getTotalPrice());
        }

        Status findStatus = this.statusRepository.findById((long) StatusConst.CONFTRM).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Status", StatusConst.CONFTRM))
        );

        newOrders.setStatus(findStatus);


        Address findAddress = this.addressRepository.findById(addressId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("address id", addressId))
        );

        newOrders.setAddress(findAddress);

        this.ordersRepository.save(newOrders);

        List<CartDetails> cartDetailsList = findUser.getCart().getCartDetailsList();

        for (int i = 0; i < cartDetailsList.size(); i++) {
            cartDetailsList.get(i).setOrders(newOrders);
            this.cartDetailsRepositoy.save(cartDetailsList.get(i));
        }

        // Xóa các cartDetails đã lưu từ cart
//        findUser.getCart().getCartDetailsList().clear();
////            findUser.getCart().setTotalPrice(0);
//        this.cartRepository.save(findUser.getCart());

        // Xóa các CartDetails
        for (CartDetails cartDetails : cartDetailsList) {
            cartDetails.setCart(null);
//            this.cartDetailsRepositoy.delete(cartDetails);
            this.cartDetailsRepositoy.save(cartDetails);
        }

        findUser.getCart().setTotalPrice(0);
        this.cartRepository.save(findUser.getCart());

        return toDTO(newOrders);
    }

    @Override
    public List<DoanhThuThang> getDoanhThuThang() {

        return this.ordersRepository.getDoanhThuThang();
    }

    @Override
    public OrdersDTORes getById(long id) {

        Orders finOrders = this.ordersRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Orders id", id))
        );

        OrdersDTORes ordersDTORes = toDTO2(finOrders);

        return ordersDTORes;

    }

    @Override
    public List<OrdersDTORes> getAllByUser(long userId) {

        User findUser = this.userReopsitory.findById(userId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("User id", userId))
        );

        List<Orders> ordersList = this.ordersRepository.findByUserId(userId);

        List<OrdersDTORes> ordersDTOResList = ordersList.stream().map(o -> toDTO2(o)).toList();

        return ordersDTOResList;
    }

    @Override
    public List<OrdersDTORes> getAllByStatusId(long statusId,long userId) {

        Status findStatus = this.statusRepository.findById(statusId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Status id", statusId))
        );

        User findUser = this.userReopsitory.findById(userId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("User id", userId))
        );

        List<Orders> ordersList = this.ordersRepository.findByUserIdAndStatusId(userId,statusId);

        List<OrdersDTORes> ordersDTOResList = ordersList.stream().map(o -> toDTO2(o)).toList();

        return ordersDTOResList;
    }

    @Override
    public OrdersDTORes cancelOrders(long ordersId) {

        Orders findOrders = this.ordersRepository.findById(ordersId)
                .orElseThrow(
                        ()-> new NotFoundException(Collections.singletonMap("Orders Id",ordersId))
                );

        Status status = this.statusRepository.findById(5L).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Status id",5))
        );

        findOrders.setStatus(status);

        this.ordersRepository.save(findOrders);

        return toDTO2(findOrders);

    }

    @Override
    public OrdersDTORes againOrders(long ordersId) {

        Orders findOrders = this.ordersRepository.findById(ordersId)
                .orElseThrow(
                        ()-> new NotFoundException(Collections.singletonMap("Orders Id",ordersId))
                );

        Status status = this.statusRepository.findById(1L).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Status id",1))
        );

        findOrders.setStatus(status);
        findOrders.setOrderDate(new Date());

        this.ordersRepository.save(findOrders);

        return toDTO2(findOrders);
    }

    @Override
    public List<OrdersDTORes> getAll() {

        List<Orders> ordersList = this.ordersRepository.findAll();
        List<OrdersDTORes> ordersDTOResList = ordersList.stream().map(o -> toDTO2(o)).toList();

        return ordersDTOResList;

    }

    @Override
    public List<OrdersDTORes> getAllByStatus(long statusId) {
        Status findStatus = this.statusRepository.findById(statusId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Status id", statusId))
        );


        List<Orders> ordersList = this.ordersRepository.findByStatusId(statusId);

        List<OrdersDTORes> ordersDTOResList = ordersList.stream().map(o -> toDTO2(o)).toList();

        return ordersDTOResList;
    }

    @Override
    public OrdersDTORes updateByStatus(long ordersId, long statusId) {
        Orders findOrders = this.ordersRepository.findById(ordersId)
                .orElseThrow(
                        ()-> new NotFoundException(Collections.singletonMap("Orders Id",ordersId))
                );

        Status status = this.statusRepository.findById(statusId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Status id",statusId))
        );

        findOrders.setStatus(status);

        this.ordersRepository.save(findOrders);

        if(statusId==4){
            List<CartDetails> cartDetailsList = findOrders.getCartDetailsList();
            for (CartDetails cartDetails:cartDetailsList){
                Product finProduct = cartDetails.getProduct();
                finProduct.setSold(finProduct.getSold()+cartDetails.getSoLuong());
                this.productRepository.save(finProduct);
            }
        }

        return toDTO2(findOrders);
    }

    @Override
    public long getCount(long statusId) {

        Status status = this.statusRepository.findById(statusId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Status id",statusId))
        );

        return this.ordersRepository.countByStatusId(statusId);

    }

    @Override
    public OrdersDTORes cancelOrdersByAdmin(long ordersId) {

        Orders findOrders = this.ordersRepository.findById(ordersId)
                .orElseThrow(
                        ()-> new NotFoundException(Collections.singletonMap("Orders Id",ordersId))
                );

        Status status = this.statusRepository.findById(5L).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Status id",5))
        );

        findOrders.setStatus(status);

        this.ordersRepository.save(findOrders);

        User finUser = findOrders.getUser();
        MailDTPRequest mailDTPRequest = new MailDTPRequest();
        mailDTPRequest.setTo(finUser.getEmail());
        mailDTPRequest.setSubject("Hủy đơn");
        mailDTPRequest.setBody("Đơn hàng của bạn đã bị hủy do vài lí do,xin lỗi vì sự bất tiện này," +
                "vào app kiểm tra và đặt hàng lại");
        mailService.send(mailDTPRequest);

        return toDTO2(findOrders);

    }

    @Override
    public double getRevenue() {
        double revenue = this.ordersRepository.getRevenue();

        return revenue;
    }



//    public OrdersDTOResponse toDTO(Orders orders) {
//        OrdersDTOResponse ordersDTOResponse = new OrdersDTOResponse();
//        ordersDTOResponse.setId(orders.getId());
//        ordersDTOResponse.setStatus(orders.getStatus().getName());
//        ordersDTOResponse.setOrderDate(orders.getOrderDate());
//        ordersDTOResponse.setTotalPrice(orders.getTotalPrice());
//        ordersDTOResponse.setAddressDTOResponse(this.addressMapper.toDTO(orders.getAddress()));
//        return ordersDTOResponse;
//    }

    public OrdersDTORes toDTO(Orders orders) {
        OrdersDTORes ordersDTOResponse = new OrdersDTORes();
        ordersDTOResponse.setId(orders.getId());
        ordersDTOResponse.setStatus(orders.getStatus().getName());
        ordersDTOResponse.setOrderDate(orders.getOrderDate());
        ordersDTOResponse.setTotalPrice(orders.getTotalPrice());
        ordersDTOResponse.setAddressDTOResponse(this.addressMapper.toDTO(orders.getAddress()));

//        List<CartDetailsDTOResponse> cartDetailsDTOResponseList = orders.getCartDetailsList()
//                .stream().map(c -> toDTO(c)).toList();
//
//        ordersDTOResponse.setCartDetailsDTOResponseList(cartDetailsDTOResponseList);

        return ordersDTOResponse;
    }

    public OrdersDTORes toDTO2(Orders orders) {
        OrdersDTORes ordersDTOResponse = new OrdersDTORes();
        ordersDTOResponse.setId(orders.getId());
        ordersDTOResponse.setStatus(orders.getStatus().getName());
        ordersDTOResponse.setOrderDate(orders.getOrderDate());
        ordersDTOResponse.setTotalPrice(orders.getTotalPrice());
        ordersDTOResponse.setAddressDTOResponse(this.addressMapper.toDTO(orders.getAddress()));

        List<CartDetailsDTOResponse> cartDetailsDTOResponseList = orders.getCartDetailsList()
                .stream().map(c -> toDTO(c)).toList();

        ordersDTOResponse.setCartDetailsDTOResponseList(cartDetailsDTOResponseList);
        return ordersDTOResponse;
    }

    public CartDetailsDTOResponse toDTO(CartDetails cartDetails) {

        CartDetailsDTOResponse cartDetailsDTOResponse = new CartDetailsDTOResponse();

        cartDetailsDTOResponse.setId(cartDetails.getId());
        cartDetailsDTOResponse.setNameSP(cartDetails.getProduct().getName());
        cartDetailsDTOResponse.setImgSP(cartDetails.getProduct().getImg().getName());
        cartDetailsDTOResponse.setPriceSP(cartDetails.getPriceSP());
        cartDetailsDTOResponse.setSoluongSP(cartDetails.getSoLuong());

        return cartDetailsDTOResponse;

    }
}
