package raiffeisen.ecom.sdk.client;

import raiffeisen.ecom.sdk.exception.CryptoException;
import raiffeisen.ecom.sdk.exception.MappingException;
import raiffeisen.ecom.sdk.exception.RequestException;
import raiffeisen.ecom.sdk.exception.ValidationException;
import raiffeisen.ecom.sdk.mapper.Mapper;
import raiffeisen.ecom.sdk.model.PaymentNotice;
import raiffeisen.ecom.sdk.model.refund.OrderRefundResponseData;
import raiffeisen.ecom.sdk.model.refund.Refund;
import raiffeisen.ecom.sdk.model.transaction.TransactionResponseData;
import raiffeisen.ecom.sdk.service.CheckSignatureService;
import raiffeisen.ecom.sdk.utils.PropertiesLoader;
import raiffeisen.ecom.sdk.web.WebClient;
import raiffeisen.ecom.sdk.web.query.OrderRefundQueryDescription;
import raiffeisen.ecom.sdk.web.query.QueryDescription;
import raiffeisen.ecom.sdk.web.query.StatusRefundQueryDescription;
import raiffeisen.ecom.sdk.web.query.TransactionInfoQueryDescription;

public class EcomClient {
    public static final String TEST_URL = PropertiesLoader.TEST_URL;
    public static final String PRODUCTION_URL = PropertiesLoader.PRODUCTION_URL;

    private final String host;
    private final String secretKey;
    private final WebClient webClient;
    private final Mapper mapper;

    public EcomClient(String host, String secretKey) {
        this.host = host;
        this.secretKey = secretKey;
        this.webClient = new WebClient();
        this.mapper = new Mapper();
    }

    public EcomClient(String secretKey) {
        this(PRODUCTION_URL, secretKey);
    }

    public TransactionResponseData getTransaction(String orderId) throws ValidationException, RequestException, MappingException {
        QueryDescription queryDescription = new TransactionInfoQueryDescription(host, secretKey, orderId);
        return mapper.mappingToObject(getResponse(queryDescription), TransactionResponseData.class);
    }

    public OrderRefundResponseData createRefund(String orderId, String refundId, Refund refund) throws ValidationException, RequestException, MappingException {
        QueryDescription queryDescription = new OrderRefundQueryDescription(host, secretKey, orderId, refundId, refund);
        return mapper.mappingToObject(getResponse(queryDescription), OrderRefundResponseData.class);
    }

    public OrderRefundResponseData getRefundInfo(String orderId, String refundId) throws ValidationException, RequestException, MappingException {
        QueryDescription queryDescription = new StatusRefundQueryDescription(host, secretKey, orderId, refundId);
        return mapper.mappingToObject(getResponse(queryDescription), OrderRefundResponseData.class);
    }

    public PaymentNotice parsingPaymentNotice(String jsonPaymentNotice) throws MappingException {
        return mapper.mappingToObject(jsonPaymentNotice, PaymentNotice.class);
    }

    public boolean checkEventSignature(String signature, PaymentNotice eventBody, String publicId) throws CryptoException {
        CheckSignatureService service = new CheckSignatureService(this.secretKey, publicId);
        return service.checkEventSignature(signature, eventBody);
    }

    private String getResponse(QueryDescription handler) throws ValidationException, RequestException, MappingException {
        return webClient.send(handler);
    }
}
