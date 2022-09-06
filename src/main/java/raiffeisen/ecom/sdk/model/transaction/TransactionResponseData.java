package raiffeisen.ecom.sdk.model.transaction;

import raiffeisen.ecom.sdk.model.ResponseData;

/**
 * Ответ на запрос статуса транзакции
 */
public class TransactionResponseData extends ResponseData {
    /**
     * Информация по транзакции
     */
    private TransactionInfo transaction;

    public TransactionResponseData() {
    }

    public TransactionInfo getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionInfo transaction) {
        this.transaction = transaction;
    }
}
