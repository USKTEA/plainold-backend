package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.FindUserService;
import com.usktea.plainold.dtos.UserInformationDto;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    private final FindUserService findUserService;

    public UserController(FindUserService findUserService) {
        this.findUserService = findUserService;
    }

    @GetMapping("me")
    public UserInformationDto user(
            @RequestAttribute Username username) {
        Users user = findUserService.find(username);

        return new UserInformationDto(user.username());
    }

    @ExceptionHandler(UserNotExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNotExists(Exception exception) {
        return exception.getMessage();
    }
}
