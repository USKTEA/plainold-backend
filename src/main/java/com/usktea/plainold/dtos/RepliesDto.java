package com.usktea.plainold.dtos;

import java.util.ArrayList;
import java.util.List;

public class RepliesDto {
    private List<ReplyDto> replies = new ArrayList<>();

    public RepliesDto() {
    }

    public RepliesDto(List<ReplyDto> replies) {
        this.replies = replies;
    }

    public List<ReplyDto> getReplies() {
        return replies;
    }
}
