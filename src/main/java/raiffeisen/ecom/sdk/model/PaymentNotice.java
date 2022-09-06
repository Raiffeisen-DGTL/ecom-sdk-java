package raiffeisen.ecom.sdk.model;

import raiffeisen.ecom.sdk.model.transaction.TransactionInfo;

/**
 * Уведомление о платеже
 */
public class PaymentNotice {
    /**
     * Тип сообщения
     */
    private String event;
    /**
     * Данные по операции
     */
    private TransactionInfo transaction;

    public PaymentNotice() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public TransactionInfo getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionInfo transaction) {
        this.transaction = transaction;
    }
}
