package raiffeisen.ecom.sdk.web.query;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.sdk.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StatusRefundQueryDescriptionTest {
    @Test
    void checkRequestParameters() throws Exception {
        String testUrl = "https://test.ecom.raiffeisen.ru/api/payments/v1/orders/order-test/refunds/refund-test";

        StatusRefundQueryDescription queryDescription = new StatusRefundQueryDescription(TestUtils.TEST_DOMAIN,
                TestUtils.TEST_SECRET_KEY,
                TestUtils.TEST_ORDER_ID,
                TestUtils.TEST_REFUND_ID);

        assertEquals(testUrl, queryDescription.getUrlRequest());
        assertEquals("GET", queryDescription.getMethodRequest());
        assertFalse(queryDescription.getBodyRequest().isPresent());
        assertEquals(queryDescription.getAuthorizationHeader(), QueryDescription.BEARER + TestUtils.TEST_SECRET_KEY);
    }
}
