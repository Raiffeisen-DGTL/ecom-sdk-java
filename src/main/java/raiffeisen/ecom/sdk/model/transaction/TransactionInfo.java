package raiffeisen.ecom.sdk.model.transaction;

import raiffeisen.ecom.sdk.model.Extra;

import java.math.BigDecimal;

/**
 * Данные по операции
 */
public class TransactionInfo {
    private Long id;
    private String orderId;
    private TransactionStatus status;
    private String paymentMethod;
    private PaymentParams paymentParams;
    /**
     * Сумма в рублях
     */
    private BigDecimal amount;
    /**
     * Комментарий
     */
    private String comment;
    /**
     * Структура с дополнительными параметрами от магазина
     */
    private Extra extra;

    public TransactionInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentParams getPaymentParams() {
        return paymentParams;
    }

    public void setPaymentParams(PaymentParams paymentParams) {
        this.paymentParams = paymentParams;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }
}
