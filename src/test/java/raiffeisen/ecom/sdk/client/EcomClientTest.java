package raiffeisen.ecom.sdk.client;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.sdk.TestUtils;
import raiffeisen.ecom.sdk.model.PaymentNotice;
import raiffeisen.ecom.sdk.model.transaction.PaymentParams;
import raiffeisen.ecom.sdk.model.transaction.StatusEnum;
import raiffeisen.ecom.sdk.model.transaction.TransactionInfo;
import raiffeisen.ecom.sdk.model.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EcomClientTest {
    @Test
    void successParsingNotification() throws Exception {
        String json = "{" +
                "\"event\": \"payment\"," +
                "\"transaction\": {" +
                "\"id\": 120059," +
                "\"orderId\": \"testOrder\"," +
                "\"status\": {" +
                "\"value\": \"SUCCESS\"," +
                "\"date\": \"2019-07-11T17:45:13+03:00\"" +
                "}," +
                "\"paymentMethod\": \"sbp\"," +
                "\"paymentParams\": {" +
                "\"qrId\": \"AD100051KNSNR64I98CRUJUASC9M72QT\"" +
                "}," +
                "\"amount\": 12500.5," +
                "\"comment\": \"Покупка шоколадного торта\"," +
                "\"extra\": {" +
                "\"additionalInfo\": \"Sweet Cake\"" +
                "}" +
                "}" +
                "}";

        EcomClient ecomClient = new EcomClient(
                TestUtils.TEST_DOMAIN, TestUtils.TEST_SECRET_KEY);
        PaymentNotice paymentNotice = ecomClient.parsingPaymentNotice(json);

        assertEquals("payment", paymentNotice.getEvent());
        TransactionInfo transaction = paymentNotice.getTransaction();
        assertEquals(120059L, transaction.getId());
        assertEquals("testOrder", transaction.getOrderId());
        TransactionStatus status = transaction.getStatus();
        assertEquals(StatusEnum.SUCCESS, status.getValue());
        assertEquals(OffsetDateTime.of(
                LocalDate.of(2019, Month.JULY, 11),
                LocalTime.of(17, 45, 13),
                ZoneOffset.of("+03:00")), status.getDate());
        assertEquals("sbp", transaction.getPaymentMethod());
        PaymentParams paymentParams = transaction.getPaymentParams();
        assertEquals("AD100051KNSNR64I98CRUJUASC9M72QT", paymentParams.getQrId());
        assertEquals(BigDecimal.valueOf(12500.5), transaction.getAmount());
        assertEquals("Покупка шоколадного торта", transaction.getComment());
        assertEquals("Sweet Cake", transaction.getExtra().getAdditionalInfo());
    }

    @Test
    void successCheckSignature() throws Exception {
        String publicId = "000001780894001-80894001";
        String signature = "7388029f82e0d7c8d6a5b06d84edaa8c0d1b10bd74d6a582c75c305411cfab97";
        PaymentNotice paymentNotice = new PaymentNotice();
        paymentNotice.setEvent("payment");
        TransactionInfo transaction = new TransactionInfo();
        transaction.setAmount(BigDecimal.valueOf(12500.5));
        transaction.setOrderId("order-test");
        transaction.setStatus(new TransactionStatus(StatusEnum.SUCCESS, OffsetDateTime.of(
                LocalDate.of(2019, Month.JULY, 11),
                LocalTime.of(17, 45, 13),
                ZoneOffset.of("+03:00"))));
        paymentNotice.setTransaction(transaction);

        EcomClient ecomClient = new EcomClient(
                TestUtils.TEST_DOMAIN, TestUtils.TEST_SECRET_KEY);

        assertTrue(ecomClient.checkEventSignature(signature, paymentNotice, publicId));
    }

    @Test
    void unsuccessfulCheckSignature() throws Exception {
        String publicId = "000001780894001-80894002";
        String signature = "7388029f82e0d7c8d6a5b06d84edaa8c0d1b10bd74d6a582c75c305411cfab97";
        PaymentNotice paymentNotice = new PaymentNotice();
        paymentNotice.setEvent("payment");
        TransactionInfo transaction = new TransactionInfo();
        transaction.setAmount(BigDecimal.valueOf(12500.5));
        transaction.setOrderId("order-test");
        transaction.setStatus(new TransactionStatus(StatusEnum.SUCCESS, OffsetDateTime.of(
                LocalDate.of(2019, Month.JULY, 11),
                LocalTime.of(17, 45, 13),
                ZoneOffset.of("+03:00"))));
        paymentNotice.setTransaction(transaction);

        EcomClient ecomClient = new EcomClient(
                TestUtils.TEST_DOMAIN, TestUtils.TEST_SECRET_KEY
        );

        assertFalse(ecomClient.checkEventSignature(signature, paymentNotice, publicId));
    }
}
