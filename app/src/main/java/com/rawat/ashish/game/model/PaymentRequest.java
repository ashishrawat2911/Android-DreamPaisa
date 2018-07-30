package com.rawat.ashish.game.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRequest {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("payment_request_id")
    @Expose
    private Integer paymentRequestId;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaymentRequest() {
    }

    /**
     *
     * @param result
     * @param status
     * @param paymentRequestId
     */
    public PaymentRequest(String status, String result, Integer paymentRequestId) {
        super();
        this.status = status;
        this.result = result;
        this.paymentRequestId = paymentRequestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getPaymentRequestId() {
        return paymentRequestId;
    }

    public void setPaymentRequestId(Integer paymentRequestId) {
        this.paymentRequestId = paymentRequestId;
    }

}