package com.huuduc.snacksnap.repository;

import com.huuduc.snacksnap.data.dto.DoanhThuThang;
import com.huuduc.snacksnap.data.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByUserId(long userId);

    List<Orders> findByStatusId(long statusId);

    List<Orders> findByUserIdAndStatusId(long userId,long statusId);

    Long countByStatusId(long statusId);

    @Query(value = "SELECT SUM(o.totalPrice) FROM Orders o WHERE o.status.id=4")
    double getRevenue();

    @Query(value = "SELECT new com.huuduc.snacksnap.data.dto.DoanhThuThang(MONTH(o.orderDate),SUM(o.totalPrice)) " +
            "FROM Orders o " +
            "WHERE o.status.id=4" +
            "GROUP BY YEAR(o.orderDate)," +
            "MONTH(o.orderDate)" +
            "ORDER BY YEAR(o.orderDate)," +
            "MONTH(o.orderDate)")
    List<DoanhThuThang> getDoanhThuThang();

}
