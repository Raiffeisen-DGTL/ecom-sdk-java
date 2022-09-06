package raiffeisen.ecom.sdk.web.query;

public class StatusRefundQueryDescription extends AbstractQueryDescription {
    private final String orderId;
    private final String refundId;


    public StatusRefundQueryDescription(String domain, String secretKey, String orderId, String refundId) {
        super(domain, secretKey);
        this.orderId = orderId;
        this.refundId = refundId;
    }

    @Override
    public String getUrlRequest() {
        String URL = "/payments/v1/orders/%s/refunds/%s";
        return createUri(String.format(URL, orderId, refundId));
    }
}
