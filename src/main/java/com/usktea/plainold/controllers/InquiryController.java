package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.inquiry.DeleteInquiryService;
import com.usktea.plainold.applications.inquiry.CreateInquiryService;
import com.usktea.plainold.applications.inquiry.EditInquiryService;
import com.usktea.plainold.applications.inquiry.GetInquiryService;
import com.usktea.plainold.dtos.CreateInquiryRequest;
import com.usktea.plainold.dtos.CreateInquiryRequestDto;
import com.usktea.plainold.dtos.CreateInquiryResultDto;
import com.usktea.plainold.dtos.DeleteInquiryResultDto;
import com.usktea.plainold.dtos.EditInquiryRequest;
import com.usktea.plainold.dtos.EditInquiryRequestDto;
import com.usktea.plainold.dtos.EditInquiryResultDto;
import com.usktea.plainold.dtos.GetInquiriesRequestDto;
import com.usktea.plainold.dtos.GetInquiriesResultDto;
import com.usktea.plainold.dtos.InquiryViewDto;
import com.usktea.plainold.dtos.PageDto;
import com.usktea.plainold.exceptions.CreateInquiryFailed;
import com.usktea.plainold.exceptions.DeleteInquiryFailed;
import com.usktea.plainold.exceptions.EditInquiryFailed;
import com.usktea.plainold.exceptions.GuestIsNotAuthorized;
import com.usktea.plainold.exceptions.NotHaveDeleteInquiryAuthority;
import com.usktea.plainold.exceptions.NotHaveEditInquiryAuthority;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.inquiry.InquiryView;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("inquiries")
public class InquiryController {
    private final GetInquiryService getInquiryService;
    private final CreateInquiryService createInquiryService;
    private final EditInquiryService editInquiryService;
    private final DeleteInquiryService deleteInquiryService;

    public InquiryController(GetInquiryService getInquiryService,
                             CreateInquiryService createInquiryService,
                             EditInquiryService editInquiryService,
                             DeleteInquiryService deleteInquiryService) {
        this.getInquiryService = getInquiryService;
        this.createInquiryService = createInquiryService;
        this.editInquiryService = editInquiryService;
        this.deleteInquiryService = deleteInquiryService;
    }

    @GetMapping
    public GetInquiriesResultDto list(
            @RequestAttribute Username username,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            HttpServletResponse response
    ) {
        GetInquiriesRequestDto getInquiriesRequestDto = new GetInquiriesRequestDto(
                username, new ProductId(productId), pageNumber);

        Page<InquiryView> inquiries = getInquiryService.inquiries(getInquiriesRequestDto);

        if (inquiries.isEmpty()) {
            response.setStatus(204);
        }

        List<InquiryViewDto> inquiryViewDtos = inquiries
                .getContent()
                .stream()
                .map(InquiryView::toDto)
                .collect(Collectors.toList());

        PageDto pageDto = new PageDto(
                pageNumber, inquiries.getTotalPages(), inquiries.getTotalElements());

        return new GetInquiriesResultDto(inquiryViewDtos, pageDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateInquiryResultDto create(
            @RequestAttribute Username username,
            @Valid @RequestBody CreateInquiryRequestDto createInquiryRequestDto
    ) {
        try {
            CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.of(createInquiryRequestDto);

            Long createdId = createInquiryService.create(username, createInquiryRequest);

            return new CreateInquiryResultDto(createdId);
        } catch (GuestIsNotAuthorized guestIsNotAuthorized) {
            throw new GuestIsNotAuthorized();
        } catch (Exception exception) {
            throw new CreateInquiryFailed(exception);
        }
    }

    @PatchMapping
    public EditInquiryResultDto edit(
            @RequestAttribute Username username,
            @Valid @RequestBody EditInquiryRequestDto editInquiryRequestDto
    ) {
        try {
            EditInquiryRequest editInquiryRequest = EditInquiryRequest.of(editInquiryRequestDto);

            Long edited = editInquiryService.edit(username, editInquiryRequest);

            return new EditInquiryResultDto(edited);
        } catch (NotHaveEditInquiryAuthority notHaveEditInquiryAuthority) {
            throw new NotHaveEditInquiryAuthority();
        } catch (Exception exception) {
            throw new EditInquiryFailed(exception.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public DeleteInquiryResultDto delete(
            @RequestAttribute Username username,
            @PathVariable Long id
    ) {
        try {
            Long deleted = deleteInquiryService.delete(username, id);

            return new DeleteInquiryResultDto(deleted);
        } catch (NotHaveDeleteInquiryAuthority notHaveDeleteInquiryAuthority) {
            throw new NotHaveDeleteInquiryAuthority();
        } catch (Exception exception) {
            throw new DeleteInquiryFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String incompleteCreateRequest() {
        return "상품문의 정보 누락";
    }

    @ExceptionHandler(GuestIsNotAuthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notAuthorized(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotHaveEditInquiryAuthority.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notHaveEditAuthority(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(NotHaveDeleteInquiryAuthority.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notHaveDeleteAuthority(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CreateInquiryFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createdInquiryFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EditInquiryFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String editInquiryFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DeleteInquiryFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String deleteInquiryFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productNotFound(Exception exception) {
        return exception.getMessage();
    }
}
