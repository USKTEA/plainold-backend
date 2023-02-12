package com.usktea.plainold.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public class KakaoPayProperties {
    private String cid;
    private String adminKey;
    private String readyUri;
    private String approvalUrl;
    private String cancelUrl;
    private String failURl;
    private String texFreeAmount;
    private String paymentProvider;
    private String approveUri;

    public KakaoPayProperties() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }

    public String getReadyUri() {
        return readyUri;
    }

    public void setReadyUri(String readyUri) {
        this.readyUri = readyUri;
    }

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getFailURl() {
        return failURl;
    }

    public void setFailURl(String failURl) {
        this.failURl = failURl;
    }

    public String getTexFreeAmount() {
        return texFreeAmount;
    }

    public void setTexFreeAmount(String texFreeAmount) {
        this.texFreeAmount = texFreeAmount;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public String getApproveUri() {
        return approveUri;
    }

    public void setApproveUri(String approveUri) {
        this.approveUri = approveUri;
    }
}
