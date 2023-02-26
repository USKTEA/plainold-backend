package com.usktea.plainold.dtos;

import java.util.List;

public class LikesDtos {
    private List<LikeDto> likes;

    public LikesDtos() {
    }

    public LikesDtos(List<LikeDto> likes) {
        this.likes = likes;
    }

    public List<LikeDto> getLikes() {
        return likes;
    }
}
