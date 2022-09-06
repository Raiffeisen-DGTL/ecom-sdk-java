package raiffeisen.ecom.sdk.model.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public class TransactionStatus {
    private StatusEnum value;
    private OffsetDateTime date;

    public TransactionStatus() {
    }

    public TransactionStatus(StatusEnum value, OffsetDateTime date) {
        this.value = value;
        this.date = date;
    }

    public StatusEnum getValue() {
        return value;
    }

    public void setValue(StatusEnum value) {
        this.value = value;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }
}
