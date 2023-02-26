package com.usktea.plainold.models.like;

import com.usktea.plainold.exceptions.UsernameNotMatch;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LikesTest {
    @Test
    void authenticate() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("otherUser@gmail.com");

        Likes like = Likes.fake(username);

        assertThrows(UsernameNotMatch.class, () -> like.authenticate(otherUsername));
    }
}
