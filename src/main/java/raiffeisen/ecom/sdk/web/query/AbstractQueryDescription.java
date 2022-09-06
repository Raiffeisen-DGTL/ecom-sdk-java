package raiffeisen.ecom.sdk.web.query;

import raiffeisen.ecom.sdk.exception.MappingException;
import raiffeisen.ecom.sdk.exception.ValidationException;

import java.util.Optional;

public abstract class AbstractQueryDescription
        implements QueryDescription {
    protected final String domain;
    protected final String secretKey;

    public AbstractQueryDescription(String domain, String secretKey) {
        this.domain = domain;
        this.secretKey = secretKey;
    }

    @Override
    public String getAuthorizationHeader() {
        return BEARER + secretKey;
    }

    @Override
    public String getMethodRequest() {
        return METHOD_GET;
    }

    @Override
    public Optional<String> getBodyRequest() throws MappingException, ValidationException {
        return Optional.empty();
    }

    protected String createUri(String url) {
        return domain + "/api" + url;
    }
}
