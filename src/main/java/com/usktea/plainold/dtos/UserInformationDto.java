package com.usktea.plainold.dtos;

import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;

public class UserInformationDto {
    private String username;
    private String nickname;
    private Long purchaseAmount;
    private String role;

    public UserInformationDto(String username,
                              String nickname,
                              Long purchaseAmount,
                              String role) {
        this.username = username;
        this.nickname = nickname;
        this.purchaseAmount = purchaseAmount;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public String getRole() {
        return role;
    }
}
