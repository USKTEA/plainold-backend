package com.usktea.plainold.dtos;

public class KakaoReadyResponse {
    private String tid;
    private String next_redirect_pc_url;

    public KakaoReadyResponse() {
    }

    public KakaoReadyResponse(String tid, String next_redirect_pc_url) {
        this.tid = tid;
        this.next_redirect_pc_url = next_redirect_pc_url;
    }

    public String getTid() {
        return tid;
    }

    public String getNext_redirect_pc_url() {
        return next_redirect_pc_url;
    }
}
