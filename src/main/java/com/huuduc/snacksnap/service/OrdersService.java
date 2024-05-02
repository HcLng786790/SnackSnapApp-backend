package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.OrdersDTORes;
import com.huuduc.snacksnap.data.dto.OrdersDTOResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrdersService {
    OrdersDTOResponse order(long userId,int type,long addressId);

    OrdersDTORes order2(long userId, int type, long addressId);

    OrdersDTORes getById(long id);

    List<OrdersDTORes> getAllByUser(long userId);

    List<OrdersDTORes> getAllByStatusId(long statusId,long userId);

    OrdersDTORes cancelOrders(long ordersId);

    OrdersDTORes againOrders(long ordersId);

    List<OrdersDTORes> getAll();

    List<OrdersDTORes> getAllByStatus(long statusId);

    OrdersDTORes updateByStatus(long ordersId, long statusId);

    long getCount(long statusId);

    OrdersDTORes cancelOrdersByAdmin(long ordersId);

    double getRevenue();
}
