package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.user.CountUserService;
import com.usktea.plainold.applications.user.CreateUserService;
import com.usktea.plainold.applications.user.EditUserService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.CountUserResultDto;
import com.usktea.plainold.dtos.CreateUserRequest;
import com.usktea.plainold.dtos.CreateUserRequestDto;
import com.usktea.plainold.dtos.CreateUserResultDto;
import com.usktea.plainold.dtos.EditUserRequest;
import com.usktea.plainold.dtos.EditUserRequestDto;
import com.usktea.plainold.dtos.EditUserResultDto;
import com.usktea.plainold.dtos.UserInformationDto;
import com.usktea.plainold.exceptions.CountUserFailed;
import com.usktea.plainold.exceptions.CreateUserFailed;
import com.usktea.plainold.exceptions.EditUserFailed;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    private final GetUserService getUserService;
    private final EditUserService editUserService;
    private final CountUserService countUserService;
    private final CreateUserService createUserService;

    public UserController(GetUserService getUserService,
                          EditUserService editUserService,
                          CountUserService countUserService,
                          CreateUserService createUserService) {
        this.getUserService = getUserService;
        this.editUserService = editUserService;
        this.countUserService = countUserService;
        this.createUserService = createUserService;
    }

    @GetMapping
    public CountUserResultDto count(
            @RequestParam String username
    ) {
        try {
            Integer counts = countUserService.count(new Username(username));

            return new CountUserResultDto(counts);
        } catch (Exception exception) {
            throw new CountUserFailed(exception.getMessage());
        }
    }

    @GetMapping("me")
    public UserInformationDto user(
            @RequestAttribute Username username) {
        Users user = getUserService.find(username);

        return user.toDto();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResultDto create(
            @Valid @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        try {
            CreateUserRequest createUserRequest = CreateUserRequest.of(createUserRequestDto);

            Username username = createUserService.create(createUserRequest);

            return new CreateUserResultDto(username.value());
        } catch (Exception exception) {
            throw new CreateUserFailed(exception.getMessage());
        }
    }

    @PatchMapping
    public EditUserResultDto edit(
            @RequestAttribute Username username,
            @Valid @RequestBody EditUserRequestDto editUserRequestDto
    ) {
        try {
            EditUserRequest editUserRequest = EditUserRequest.of(editUserRequestDto);

            Username edited = editUserService.edit(username, editUserRequest);

            return new EditUserResultDto(edited.value());
        } catch (Exception exception) {
            throw new EditUserFailed(exception.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String incompleteRequestInformation(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNotExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNotExists(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EditUserFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String editUserFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CountUserFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String countUserFail(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CreateUserFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createUserFailed(Exception exception) {
        return exception.getMessage();
    }
}
