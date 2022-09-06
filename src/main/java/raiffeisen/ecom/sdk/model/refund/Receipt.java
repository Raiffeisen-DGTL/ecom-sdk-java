package raiffeisen.ecom.sdk.model.refund;

import java.util.List;

/**
 * Данные чека
 */
public class Receipt {
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
