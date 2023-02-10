package com.usktea.plainold.dtos;

import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;

import java.util.function.Supplier;

public class UserProfile {
    private String email;
    private String nickname;

    public UserProfile(Builder builder) {
        this.email = builder.email;;
        this.nickname = builder.nickname;;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private String nickname;

        private Builder() {};

        public Builder email(String email) {
            this.email = email;

            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;

            return this;
        }

        public UserProfile build() {
            return new UserProfile(this);
        }
    }
}
