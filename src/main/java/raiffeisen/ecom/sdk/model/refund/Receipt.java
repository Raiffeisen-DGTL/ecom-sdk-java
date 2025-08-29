package raiffeisen.ecom.sdk.model.refund;

import java.util.List;

/**
 * Данные чека
 */
public class Receipt {
    /**
     * Признак расчета в Интернете
     */
    private Boolean onlinePayment;
    /**
     * Данные о покупателе
     */
    private ReceiptCustomer customer;
    /**
     * Позиции чека
     */
    private List<CheckPosition> items;

    public Receipt() {
    }

    public Boolean getOnlinePayment() { return onlinePayment; }

    public void setOnlinePayment(Boolean onlinePayment) { this.onlinePayment = onlinePayment; }

    public ReceiptCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(ReceiptCustomer customer) {
        this.customer = customer;
    }

    public List<CheckPosition> getItems() {
        return items;
    }

    public void setItems(List<CheckPosition> items) {
        this.items = items;
    }
}
