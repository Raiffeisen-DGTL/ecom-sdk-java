package raiffeisen.ecom.sdk.web.query;

public class TransactionInfoQueryDescription extends AbstractQueryDescription {
    private final String orderId;

    public TransactionInfoQueryDescription(String domain, String secretKey, String orderId) {
        super(domain, secretKey);
        this.orderId = orderId;
    }

    @Override
    public String getUrlRequest() {
        String URL = "/payments/v1/orders/%s/transaction";
        return createUri(String.format(URL, orderId));
    }
}
