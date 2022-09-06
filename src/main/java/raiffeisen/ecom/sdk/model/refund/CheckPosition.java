package raiffeisen.ecom.sdk.model.refund;

import java.math.BigDecimal;

/**
 * Позиция чека
 */
public class CheckPosition {
    /**
     * Наименование товара, работы, услуги, иного предмета расчета
     */
    private String name;
    /**
     * Цена за единицу товара, работы, услуги, иного предмета расчета в рублях (8 символов на целую часть, 2 на дробную)
     */
    private BigDecimal price;
    /**
     * Количество/вес (5 символов на целую часть, 3 на дробную)
     */
    private BigDecimal quantity;
    /**
     * Сумма в рублях. Должна равняться произведению цены на количество (price * quantity). 8 символов на целую часть, 2 на дробную
     */
    private BigDecimal amount;

    /**
     * Признак предмета расчёта
     */
    private PaymentObjectEnum paymentObject;

    /**
     * Способ расчета
     */
    private PaymentModeEnum paymentMode;

    /**
     * Единица измерения товара, работы, услуги, иного предмета расчета
     */
    private String measurementUnit;

    /**
     * Номенклатурный код товара в 16-ричном представлении с пробелами или в формате GS1 DataMatrix.
     * Например, '00 00 00 01 00 21 FA 41 00 23 05 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 12 00 AB 00' или '010463003407001221CMK45BrhN0WLf'
     * Если вы используете онлайн-кассу «БИФИТ», номенклатурный код может быть передан строго только в 16-ричном представлении с пробелами
     */
    private String nomenclatureCode;

    /**
     * Ставка НДС на позицию чека
     */
    private VatTypeEnum vatType;

    /**
     * Признак агента по предмету расчета. Опциональный параметр, который заполняется только для операций через агента
     */
    private String agentType;

    /**
     * Данные о поставщике. Обязательно к заполнению, если заполнен параметр agentType
     */
    private SupplierInfo supplierInfo;

    public CheckPosition() {
    }

    public CheckPosition(String name, BigDecimal price, BigDecimal quantity, BigDecimal amount, VatTypeEnum vatType) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.vatType = vatType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentObjectEnum getPaymentObject() {
        return paymentObject;
    }

    public void setPaymentObject(PaymentObjectEnum paymentObject) {
        this.paymentObject = paymentObject;
    }

    public PaymentModeEnum getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentModeEnum paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getNomenclatureCode() {
        return nomenclatureCode;
    }

    public void setNomenclatureCode(String nomenclatureCode) {
        this.nomenclatureCode = nomenclatureCode;
    }

    public VatTypeEnum getVatType() {
        return vatType;
    }

    public void setVatType(VatTypeEnum vatType) {
        this.vatType = vatType;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public SupplierInfo getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierInfo supplierInfo) {
        this.supplierInfo = supplierInfo;
    }
}
