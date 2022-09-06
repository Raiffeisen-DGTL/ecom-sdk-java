package raiffeisen.ecom.sdk.model.refund;

import raiffeisen.ecom.sdk.model.ResponseData;

import java.math.BigDecimal;

public class OrderRefundResponseData extends ResponseData {
    private BigDecimal amount;
    private String message;
    private Receipt receipt;
    private RefundStatusEnum refundStatus;

    public OrderRefundResponseData() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public RefundStatusEnum getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatusEnum refundStatus) {
        this.refundStatus = refundStatus;
    }
}
