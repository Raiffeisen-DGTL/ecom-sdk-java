package raiffeisen.ecom.sdk.model.refund;

import java.math.BigDecimal;

public class Refund {
    private final BigDecimal amount;
    private Receipt receipt;

    public Refund(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
