package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.cancelRequest.CreateCancelRequestService;
import com.usktea.plainold.applications.cancelRequest.GetCancelRequestService;
import com.usktea.plainold.dtos.CancelRequestDto;
import com.usktea.plainold.dtos.CreateCancelRequestInput;
import com.usktea.plainold.dtos.CreateCancelRequestInputDto;
import com.usktea.plainold.dtos.CreateCancelRequestResultDto;
import com.usktea.plainold.exceptions.CancelRequestNotBelongToUser;
import com.usktea.plainold.exceptions.CreateCancelRequestFailed;
import com.usktea.plainold.exceptions.GetCancelRequestFailed;
import com.usktea.plainold.models.cancelRequest.CancelRequest;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cancelRequests")
public class CancelRequestController {
    private final CreateCancelRequestService createCancelRequestService;
    private final GetCancelRequestService getCancelRequestService;

    public CancelRequestController(CreateCancelRequestService createCancelRequestService,
                                   GetCancelRequestService getCancelRequestService) {
        this.createCancelRequestService = createCancelRequestService;
        this.getCancelRequestService = getCancelRequestService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCancelRequestResultDto create(
            @RequestAttribute Username username,
            @RequestBody CreateCancelRequestInputDto createCancelRequestInputDto
    ) {
        try {
            CreateCancelRequestInput createCancelRequestInput = CreateCancelRequestInput.of(createCancelRequestInputDto);

            Long id = createCancelRequestService.create(username, createCancelRequestInput);

            return new CreateCancelRequestResultDto(id);
        } catch (Exception exception) {
            throw new CreateCancelRequestFailed(exception.getMessage());
        }
    }

    @GetMapping("{orderNumber}")
    public CancelRequestDto getCancelRequest(
            @RequestAttribute Username username,
            @PathVariable String orderNumber
    ) {
        try {
            CancelRequest cancelRequest = getCancelRequestService.getCancelRequest(
                    username, new OrderNumber(orderNumber));

            return cancelRequest.toDto();
        } catch (CancelRequestNotBelongToUser cancelRequestNotBelongToUser) {
            throw cancelRequestNotBelongToUser;
        } catch (Exception exception) {
            throw new GetCancelRequestFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(CreateCancelRequestFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createCancelRequestFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(GetCancelRequestFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String getCancelRequestFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CancelRequestNotBelongToUser.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String cancelRequestNotBelongToUser(Exception exception) {
        return exception.getMessage();
    }
}
