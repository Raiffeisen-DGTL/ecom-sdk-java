package raiffeisen.ecom.sdk.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CryptoUtilsTest {
    @Test
    void equalSignature() throws Exception {
        String signature = "96c4b6fa97866f01acabcf32116e3e1347e0e5769b8587eeb877b79180eea4cb";

        String secretKey = "test_secret_key";
        String publicId = "test_public_id";
        String controlLine = "1|" + publicId + "|test_transaction_order_id|test_transaction_status_value|test_transaction_status_date";

        String encode = CryptoUtils.encodeByHmacSHA256(secretKey, controlLine);
        assertEquals(signature, encode);
    }

    @Test
    void notEqualSignature() throws Exception {
        String signature = "96c4b6fa97866f01acabcf32116e3e1347e0e5769b8587eeb877b79180eea4cb";

        String secretKey = "test_secret_key";
        String publicId = "test_public_id";
        String controlLine = "2|" + publicId + "|test_transaction_order_id|test_transaction_status_value|test_transaction_status_date";

        String encode = CryptoUtils.encodeByHmacSHA256(secretKey, controlLine);
        assertNotEquals(signature, encode);
    }
}
