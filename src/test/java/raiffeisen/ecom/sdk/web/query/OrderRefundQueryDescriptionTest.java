package raiffeisen.ecom.sdk.web.query;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.sdk.TestUtils;
import raiffeisen.ecom.sdk.exception.ValidationException;
import raiffeisen.ecom.sdk.model.refund.CheckPosition;
import raiffeisen.ecom.sdk.model.refund.PaymentModeEnum;
import raiffeisen.ecom.sdk.model.refund.PaymentObjectEnum;
import raiffeisen.ecom.sdk.model.refund.Receipt;
import raiffeisen.ecom.sdk.model.refund.ReceiptCustomer;
import raiffeisen.ecom.sdk.model.refund.Refund;
import raiffeisen.ecom.sdk.model.refund.VatTypeEnum;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderRefundQueryDescriptionTest {
    @Test
    void checkRequestParametersWithRefundAmount() throws Exception {
        String testUrl = "https://test.ecom.raiffeisen.ru/api/payments/v1/orders/order-test/refunds/refund-test";
        String requestBodyJson = "{\"amount\":188,\"receipt\":null}";

        OrderRefundQueryDescription queryDescription = new OrderRefundQueryDescription(TestUtils.TEST_DOMAIN,
                TestUtils.TEST_SECRET_KEY,
                TestUtils.TEST_ORDER_ID,
                TestUtils.TEST_REFUND_ID,
                new Refund(BigDecimal.valueOf(188)));

        assertEquals(testUrl, queryDescription.getUrlRequest());
        assertEquals("POST", queryDescription.getMethodRequest());
        Optional<String> bodyRequest = queryDescription.getBodyRequest();
        assertTrue(queryDescription.getBodyRequest().isPresent());
        assertEquals(requestBodyJson, bodyRequest.get());
    }

    @Test
    void checkRequestParametersWithRefundFull() throws Exception {
        String testUrl = "https://test.ecom.raiffeisen.ru/api/payments/v1/orders/order-test/refunds/refund-test";
        String requestBodyJson = "{\"amount\":188,\"receipt\":{\"onlinePayment\":true,\"customer\":{\"email\":\"mail@mail.com\",\"name\":\"Иванов Иван Иванович\"},\"items\":[{\"name\":\"Шоколадный торт\",\"price\":22.5,\"quantity\":4.2,\"amount\":94.5,\"paymentObject\":\"COMMODITY\",\"paymentMode\":\"FULL_PREPAYMENT\",\"measurementUnit\":\"шт\",\"nomenclatureCode\":\"00 00 00 01 00 21 FA 41 00 23 05 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 12 00 AB 00\",\"vatType\":\"VAT0\",\"agentType\":null,\"supplierInfo\":null}]}}";

        Refund refund = new Refund(BigDecimal.valueOf(188));
        Receipt receipt = new Receipt();
        receipt.setOnlinePayment(true);
        receipt.setCustomer(new ReceiptCustomer("mail@mail.com", "Иванов Иван Иванович"));
        CheckPosition checkPosition = new CheckPosition("check-test-1",
                BigDecimal.valueOf(22.5),
                BigDecimal.valueOf(4.2),
                BigDecimal.valueOf(94.5),
                VatTypeEnum.VAT0);
        checkPosition.setName("Шоколадный торт");
        checkPosition.setPaymentObject(PaymentObjectEnum.COMMODITY);
        checkPosition.setPaymentMode(PaymentModeEnum.FULL_PREPAYMENT);
        checkPosition.setMeasurementUnit("шт");
        checkPosition.setNomenclatureCode("00 00 00 01 00 21 FA 41 00 23 05 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 12 00 AB 00");
        receipt.setItems(Collections.singletonList(checkPosition));
        refund.setReceipt(receipt);

        OrderRefundQueryDescription queryDescription = new OrderRefundQueryDescription(TestUtils.TEST_DOMAIN,
                TestUtils.TEST_SECRET_KEY,
                TestUtils.TEST_ORDER_ID,
                TestUtils.TEST_REFUND_ID,
                refund);

        assertEquals(testUrl, queryDescription.getUrlRequest());
        assertEquals("POST", queryDescription.getMethodRequest());
        Optional<String> bodyRequest = queryDescription.getBodyRequest();
        assertTrue(bodyRequest.isPresent());
        assertEquals(requestBodyJson, bodyRequest.get());
    }

    @Test
    void unsuccessfulValidateWrongAmount() {
        String testUrl = "https://test.ecom.raiffeisen.ru/api/payments/v1/orders/order-test/refunds/refund-test";

        Refund refund = new Refund(BigDecimal.valueOf(188));
        Receipt receipt = new Receipt();
        receipt.setCustomer(new ReceiptCustomer("mail@mail.com", "Иванов Иван Иванович"));
        CheckPosition checkPosition = new CheckPosition("check-test-1",
                BigDecimal.valueOf(22.5),
                BigDecimal.valueOf(4.2),
                BigDecimal.valueOf(10.5),
                VatTypeEnum.VAT0);
        checkPosition.setName("Шоколадный торт");
        checkPosition.setPaymentObject(PaymentObjectEnum.COMMODITY);
        checkPosition.setPaymentMode(PaymentModeEnum.FULL_PREPAYMENT);
        checkPosition.setMeasurementUnit("шт");
        checkPosition.setNomenclatureCode("00 00 00 01 00 21 FA 41 00 23 05 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 12 00 AB 00");
        receipt.setItems(Collections.singletonList(checkPosition));
        refund.setReceipt(receipt);

        OrderRefundQueryDescription queryDescription = new OrderRefundQueryDescription(TestUtils.TEST_DOMAIN,
                TestUtils.TEST_SECRET_KEY,
                TestUtils.TEST_ORDER_ID,
                TestUtils.TEST_REFUND_ID,
                refund);

        assertEquals(testUrl, queryDescription.getUrlRequest());
        assertEquals("POST", queryDescription.getMethodRequest());
        assertThrows(ValidationException.class, queryDescription::getBodyRequest);
    }


    @Test
    void unsuccessfulValidateCustomerWithoutEmail() {
        String testUrl = "https://test.ecom.raiffeisen.ru/api/payments/v1/orders/order-test/refunds/refund-test";

        Refund refund = new Refund(BigDecimal.valueOf(188));
        Receipt receipt = new Receipt();
        receipt.setCustomer(new ReceiptCustomer(null, "Иванов Иван Иванович"));
        CheckPosition checkPosition = new CheckPosition("check-test-1",
                BigDecimal.valueOf(22.5),
                BigDecimal.valueOf(4.2),
                BigDecimal.valueOf(10.5), VatTypeEnum.VAT0);
        checkPosition.setName("Шоколадный торт");
        checkPosition.setPaymentObject(PaymentObjectEnum.COMMODITY);
        checkPosition.setPaymentMode(PaymentModeEnum.FULL_PREPAYMENT);
        checkPosition.setMeasurementUnit("шт");
        checkPosition.setNomenclatureCode("00 00 00 01 00 21 FA 41 00 23 05 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 12 00 AB 00");
        receipt.setItems(Collections.singletonList(checkPosition));
        refund.setReceipt(receipt);

        OrderRefundQueryDescription queryDescription = new OrderRefundQueryDescription(TestUtils.TEST_DOMAIN,
                TestUtils.TEST_SECRET_KEY,
                TestUtils.TEST_ORDER_ID,
                TestUtils.TEST_REFUND_ID,
                refund);

        assertEquals(testUrl, queryDescription.getUrlRequest());
        assertEquals("POST", queryDescription.getMethodRequest());
        assertThrows(ValidationException.class, queryDescription::getBodyRequest);
    }
}
