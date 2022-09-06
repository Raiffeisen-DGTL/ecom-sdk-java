package raiffeisen.ecom.sdk.model.refund;

/**
 * Признак предмета расчёта
 */
public enum PaymentObjectEnum {
    /**
     * товар
     */
    COMMODITY,
    /**
     * подакцизный товар
     */
    EXCISE,
    /**
     * работа
     */
    JOB,
    /**
     * услуга
     */
    SERVICE,
    /**
     * платеж
     */
    PAYMENT,
    /**
     * иной предмет расчета
     */
    ANOTHER
}
