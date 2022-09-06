package raiffeisen.ecom.sdk.model.transaction;

public class PaymentParams {
    /**
     * Номер для идентификации
     */
    private Long rrn;
    /**
     * Код авторизации платежа, полученный от эмитента
     */
    private Long authCode;
    /**
     * Уникальный идентификатор QRC, выданный СБП
     */
    private String qrId;

    public PaymentParams() {
    }

    public Long getRrn() {
        return rrn;
    }

    public void setRrn(Long rrn) {
        this.rrn = rrn;
    }

    public Long getAuthCode() {
        return authCode;
    }

    public void setAuthCode(Long authCode) {
        this.authCode = authCode;
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }
}
