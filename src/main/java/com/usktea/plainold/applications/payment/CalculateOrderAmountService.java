package com.usktea.plainold.applications.payment;

import com.usktea.plainold.dtos.OrderItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateOrderAmountService {
    public Integer calculate(List<OrderItemDto> orderItems) {
        return getTotalAmount(orderItems);
    }

    private Integer getTotalAmount(List<OrderItemDto> orderItems) {
        Integer total = orderItems.stream()
                .mapToInt((orderItem) -> Math.toIntExact(orderItem.getTotalPrice()))
                .sum();

        Integer largestShippingFee = getShippingFee(orderItems);
        Integer largestFreeShippingAmount = getFreeShippingAmount(orderItems);

        if (total < largestFreeShippingAmount) {
            return total + largestShippingFee;
        }

        return total;
    }

    private Integer getFreeShippingAmount(List<OrderItemDto> orderItems) {
        return orderItems.stream()
                .mapToInt((orderItem) -> Math.toIntExact(orderItem.getFreeShippingAmount()))
                .max()
                .orElse(0);
    }

    private Integer getShippingFee(List<OrderItemDto> orderItems) {
        return orderItems.stream()
                .mapToInt((orderItem) -> Math.toIntExact(orderItem.getShippingFee()))
                .max()
                .orElse(0);
    }
}
