package raiffeisen.ecom.sdk.model.refund;

/**
 * Данные о поставщике
 */
public class SupplierInfo {
    /**
     * Телефон поставщика. Заполняется по формату "+79991234567", после кода +7 должно быть указано 10 цифр
     */
    private String phone;
    /**
     * Наименование поставщика
     */
    private String name;
    /**
     * ИНН поставщика. Может содержать только цифры в количестве 10 или 12 символов
     */
    private String inn;

    public SupplierInfo() {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }
}
