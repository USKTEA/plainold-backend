package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.payment.PaymentService;
import com.usktea.plainold.applications.payment.PaymentServiceFactory;
import com.usktea.plainold.dtos.PaymentApproveRequest;
import com.usktea.plainold.dtos.PaymentReadyRequestDto;
import com.usktea.plainold.dtos.PaymentReadyResponse;
import com.usktea.plainold.dtos.PaymentReadyResponseDto;
import com.usktea.plainold.dtos.UpdateItemInCartResultDto;
import com.usktea.plainold.exceptions.InvalidProvider;
import com.usktea.plainold.exceptions.PaymentApproveFailed;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("payment")
public class PaymentController {
    private final PaymentServiceFactory paymentServiceFactory;

    public PaymentController(PaymentServiceFactory paymentServiceFactory) {
        this.paymentServiceFactory = paymentServiceFactory;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentReadyResponseDto ready(
            @RequestAttribute Username username,
            @RequestBody PaymentReadyRequestDto paymentReadyRequestDto
    ) {
        String provider = paymentReadyRequestDto.getProvider();

        PaymentService paymentService = paymentServiceFactory.getmentPayService(provider);

        if (Objects.isNull(paymentService)) {
            throw new InvalidProvider();
        }

        PaymentReadyResponse paymentReadyResponse =
                paymentService.ready(username, paymentReadyRequestDto.getOrderItems());

        return paymentReadyResponse.toDto();
    }

    @GetMapping
    public UpdateItemInCartResultDto.PaymentApproveResultDto approve(
            @RequestAttribute Username username,
            @RequestParam("provider") String provider,
            @RequestParam("pgToken") String pgToken,
            @RequestParam("tidId") Integer tidId,
            @RequestParam("partnerOrderId") String partnerOrderId
    ) {
        PaymentService paymentService = paymentServiceFactory.getmentPayService(provider);

        if (Objects.isNull(paymentService)) {
            throw new InvalidProvider();
        }

        PaymentApproveRequest paymentApproveRequest = PaymentApproveRequest.of(provider, pgToken, tidId, partnerOrderId);

        try {
            String aid = paymentService.approve(username, paymentApproveRequest);

            return new UpdateItemInCartResultDto.PaymentApproveResultDto(aid);
        } catch (Exception exception) {
            throw new PaymentApproveFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(InvalidProvider.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidProvider(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNotExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNotExists(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(PaymentApproveFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String paymentApproveFail(Exception exception) {
        return exception.getMessage();
    }
}
