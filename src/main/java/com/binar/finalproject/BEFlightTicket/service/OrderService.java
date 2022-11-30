package com.binar.finalproject.BEFlightTicket.service;

import com.binar.finalproject.BEFlightTicket.dto.OrderRequest;
import com.binar.finalproject.BEFlightTicket.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse addOrder(OrderRequest orderRequest);
    OrderResponse updateOrder(OrderRequest orderRequest, UUID orderId);
    List<OrderResponse> getAllOrder();
    List<OrderResponse> getAllOrderByUserId(UUID userId);
    List<OrderResponse> getAllOrderByPaymentId(Integer paymentId);
}