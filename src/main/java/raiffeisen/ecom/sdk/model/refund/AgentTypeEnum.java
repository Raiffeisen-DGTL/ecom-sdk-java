package raiffeisen.ecom.sdk.model.refund;

/**
 * Признак агента по предмету расчета
 */
public enum AgentTypeEnum {
    /**
     * банковский платежный агент
     */
    BANK_PAYING_AGENT,
    /**
     * банковский платежный субагент
     */
    BANK_PAYING_SUBAGENT,
    /**
     * платежный агент
     */
    PAYING_AGENT,
    /**
     * платежный субагент
     */
    PAYING_SUBAGENT,
    /**
     * поверенный
     */
    ATTORNEY,
    /**
     * комиссионер
     */
    COMMISSION_AGENT,
    /**
     * другой тип агента
     */
    ANOTHER
}
