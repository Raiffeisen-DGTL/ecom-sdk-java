package raiffeisen.ecom.sdk.model.refund;

/**
 * Данные о покупателе при заполнении чека
 */
public class ReceiptCustomer {
    /**
     * E-mail покупателя для отправки чека. Если E-mail не передан, то чек отправляется на электронную почту мерчанта
     */
    private String email;
    /**
     * ФИО покупателя
     */
    private String name;

    public ReceiptCustomer() {
    }

    public ReceiptCustomer(String email) {
        this.email = email;
    }

    public ReceiptCustomer(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
