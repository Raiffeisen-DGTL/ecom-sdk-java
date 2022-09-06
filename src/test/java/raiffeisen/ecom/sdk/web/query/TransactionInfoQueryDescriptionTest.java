package raiffeisen.ecom.sdk.web.query;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.sdk.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TransactionInfoQueryDescriptionTest {

    @Test
    void checkRequestParameters() throws Exception {
        String testUrl = "https://test.ecom.raiffeisen.ru/api/payments/v1/orders/order-test/transaction";

        TransactionInfoQueryDescription queryDescription = new TransactionInfoQueryDescription(TestUtils.TEST_DOMAIN,
                TestUtils.TEST_SECRET_KEY,
                TestUtils.TEST_ORDER_ID);

        assertFalse(queryDescription.getBodyRequest().isPresent());
        assertEquals(testUrl, queryDescription.getUrlRequest());
        assertEquals("GET", queryDescription.getMethodRequest());
        assertEquals(queryDescription.getAuthorizationHeader(), QueryDescription.BEARER + TestUtils.TEST_SECRET_KEY);
    }
}