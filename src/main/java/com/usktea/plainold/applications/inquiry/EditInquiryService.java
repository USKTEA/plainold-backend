package com.usktea.plainold.applications.inquiry;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.EditInquiryRequest;
import com.usktea.plainold.exceptions.InquiryNotExists;
import com.usktea.plainold.models.inquiry.Inquiry;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.InquiryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EditInquiryService {
    private final GetUserService getUserService;
    private final InquiryRepository inquiryRepository;

    public EditInquiryService(GetUserService getUserService, InquiryRepository inquiryRepository) {
        this.getUserService = getUserService;
        this.inquiryRepository = inquiryRepository;
    }

    public Long edit(Username username, EditInquiryRequest editInquiryRequest) {
        Users user = getUserService.find(username);

        Inquiry inquiry = inquiryRepository.findById(editInquiryRequest.id())
                .orElseThrow(InquiryNotExists::new);

        inquiry.edit(user.username(), user.role(),
                editInquiryRequest.title(), editInquiryRequest.content());

        return inquiry.id();
    }
}
