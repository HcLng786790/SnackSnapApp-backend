package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.MailDTPRequest;
import com.huuduc.snacksnap.service.MailService;
import com.huuduc.snacksnap.service.OrdersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Orders Controller
 *
 * @author huuduc
 */
@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "bearer-key")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private MailService mailService;


    @PostMapping("/add")
    public ResponseEntity<?> order(@RequestParam long userId,
                                   @RequestParam int type,
                                   @RequestParam long addressId) {

        return ResponseEntity.ok(this.ordersService.order(userId, type, addressId));
    }

    @Transactional
    @PostMapping("/addNew")
    public ResponseEntity<?> orders2(@RequestParam long userId,
                                     @RequestParam int type,
                                     @RequestParam long addressId) {

        return ResponseEntity.ok(this.ordersService.order2(userId, type, addressId));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {

        return ResponseEntity.ok(this.ordersService.getById(id));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllByUser(@PathVariable long userId) {

        return ResponseEntity.ok(this.ordersService.getAllByUser(userId));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAllByStatus(@RequestParam long statusId,
                                            @RequestParam long userId) {

        return ResponseEntity.ok(this.ordersService.getAllByStatusId(statusId, userId));
    }

    @PutMapping("/cancel/{ordersId}")
    public ResponseEntity<?> cancelOrders(@PathVariable long ordersId) {

        return ResponseEntity.ok(this.ordersService.cancelOrders(ordersId));
    }

    @PutMapping("/again/{ordersId}")
    public ResponseEntity<?> againOrders(@PathVariable long ordersId) {

        return ResponseEntity.ok(this.ordersService.againOrders(ordersId));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<?> getAllStatusId(@PathVariable long statusId) {

        return ResponseEntity.ok(this.ordersService.getAllByStatus(statusId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateByStatus(@RequestParam long ordersId,
                                            @RequestParam long statusId) {

        return ResponseEntity.ok(this.ordersService.updateByStatus(ordersId, statusId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/{statusId}")
    public ResponseEntity<?> getCount(@PathVariable long statusId) {

        return ResponseEntity.ok(this.ordersService.getCount(statusId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/cancel/{ordersId}")
    public ResponseEntity<?> cancelOrdersByAdmin(@PathVariable long ordersId){

        return ResponseEntity.ok(this.ordersService.cancelOrdersByAdmin(ordersId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/revenue")
    public ResponseEntity<?> getRevenue(){

        return ResponseEntity.ok(this.ordersService.getRevenue());
    }

}
