package raiffeisen.ecom.sdk.model.refund;

/**
 * Способ расчета
 */
public enum PaymentModeEnum {
    /**
     * 100% предоплата до момента передачи предмета расчета
     */
    FULL_PREPAYMENT,
    /**
     * полная оплата в момент передачи предмета расчета
     * если параметр не передан, по умолчанию устанавливается значение FULL_PREPAYMENT
     */
    FULL_PAYMENT
}
