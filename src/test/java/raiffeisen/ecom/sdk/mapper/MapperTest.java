package raiffeisen.ecom.sdk.mapper;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.sdk.model.refund.CheckPosition;
import raiffeisen.ecom.sdk.model.refund.OrderRefundResponseData;
import raiffeisen.ecom.sdk.model.refund.PaymentModeEnum;
import raiffeisen.ecom.sdk.model.refund.PaymentObjectEnum;
import raiffeisen.ecom.sdk.model.refund.Receipt;
import raiffeisen.ecom.sdk.model.refund.ReceiptCustomer;
import raiffeisen.ecom.sdk.model.refund.RefundStatusEnum;
import raiffeisen.ecom.sdk.model.refund.VatTypeEnum;
import raiffeisen.ecom.sdk.model.transaction.StatusEnum;
import raiffeisen.ecom.sdk.model.transaction.TransactionInfo;
import raiffeisen.ecom.sdk.model.transaction.TransactionResponseData;
import raiffeisen.ecom.sdk.model.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapperTest {
    @Test
    void mappingTransaction() throws Exception {
        String response =
                "{" +
                        "\"code\": \"SUCCESS\"," +
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

        TransactionResponseData transactionResponse = new Mapper().mappingToObject(response, TransactionResponseData.class);
        assertEquals("SUCCESS", transactionResponse.getCode());
        TransactionInfo transactionInfo = transactionResponse.getTransaction();
        assertEquals(120059L, transactionInfo.getId());
        assertEquals("testOrder", transactionInfo.getOrderId());
        TransactionStatus status = transactionInfo.getStatus();
        assertEquals(StatusEnum.SUCCESS, status.getValue());
        assertEquals(OffsetDateTime.of(
                LocalDate.of(2019, Month.JULY, 11),
                LocalTime.of(17, 45, 13),
                ZoneOffset.of("+03:00")), status.getDate());
        assertEquals("sbp", transactionInfo.getPaymentMethod());
        assertEquals("AD100051KNSNR64I98CRUJUASC9M72QT", transactionInfo.getPaymentParams().getQrId());
        assertEquals(BigDecimal.valueOf(12500.5), transactionInfo.getAmount());
        assertEquals("Покупка шоколадного торта", transactionInfo.getComment());
        assertEquals("Sweet Cake", transactionInfo.getExtra().getAdditionalInfo());
    }

    @Test
    void mappingStatusRefund() throws Exception {
        String response = "{" +
                "\"code\": \"SUCCESS\"," +
                "\"amount\": 150," +
                "\"refundStatus\": \"COMPLETED\"" +
                "}";

        OrderRefundResponseData orderRefundResponse = new Mapper().mappingToObject(response, OrderRefundResponseData.class);
        assertEquals("SUCCESS", orderRefundResponse.getCode());
        assertEquals(BigDecimal.valueOf(150), orderRefundResponse.getAmount());
        assertEquals(RefundStatusEnum.COMPLETED, orderRefundResponse.getRefundStatus());
        assertNull(orderRefundResponse.getReceipt());
    }

    @Test
    void mappingOrderRefundWithoutCheck() throws Exception {
        String response = "{" +
                "\"code\": \"SUCCESS\"," +
                "\"amount\": 150," +
                "\"refundStatus\": \"IN_PROGRESS\"" +
                "}";
        OrderRefundResponseData orderRefundResponse = new Mapper().mappingToObject(response, OrderRefundResponseData.class);
        assertEquals("SUCCESS", orderRefundResponse.getCode());
        assertEquals(BigDecimal.valueOf(150), orderRefundResponse.getAmount());
        assertEquals(RefundStatusEnum.IN_PROGRESS, orderRefundResponse.getRefundStatus());
        assertNull(orderRefundResponse.getReceipt());
    }

    @Test
    void mappingOrderRefundWithCheck() throws Exception {
        String response = "{" +
                "\"code\": \"SUCCESS\"," +
                "\"amount\": 150," +
                "\"refundStatus\": \"IN_PROGRESS\"," +
                "\"receipt\": {" +
                "\"onlinePayment\": true," +
                "\"customer\": {" +
                "\"email\": \"customer@test.ru\"," +
                "\"name\": \"Иванов Иван Иванович\"" +
                "}," +
                "\"items\": [" +
                "{" +
                "\"name\": \"Шоколадный торт\"," +
                "\"price\": 150," +
                "\"quantity\": 1," +
                "\"amount\": 150," +
                "\"paymentObject\": \"COMMODITY\"," +
                "\"paymentMode\": \"FULL_PREPAYMENT\"," +
                "\"measurementUnit\": \"шт\"," +
                "\"nomenclatureCode\": \"00 00 00 01 00 21 FA 41 00 23 05 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 12 00 AB 00\"," +
                "\"vatType\": \"VAT20\"" +
                "}" +
                "]" +
                "}" +
                "}";
        OrderRefundResponseData orderRefundResponse = new Mapper().mappingToObject(response, OrderRefundResponseData.class);
        assertEquals("SUCCESS", orderRefundResponse.getCode());
        assertEquals(BigDecimal.valueOf(150), orderRefundResponse.getAmount());
        assertEquals(RefundStatusEnum.IN_PROGRESS, orderRefundResponse.getRefundStatus());
        Receipt receipt = orderRefundResponse.getReceipt();
        assertNotNull(receipt);
        ReceiptCustomer customer = receipt.getCustomer();
        assertEquals("Иванов Иван Иванович", customer.getName());
        assertEquals("customer@test.ru", customer.getEmail());
        assertEquals(1, receipt.getItems().size());
        CheckPosition checkPosition = receipt.getItems().get(0);
        assertEquals("Шоколадный торт", checkPosition.getName());
        assertEquals(BigDecimal.valueOf(150), checkPosition.getAmount());
        assertEquals(PaymentObjectEnum.COMMODITY, checkPosition.getPaymentObject());
        assertEquals(PaymentModeEnum.FULL_PREPAYMENT, checkPosition.getPaymentMode());
        assertEquals("шт", checkPosition.getMeasurementUnit());
        assertEquals("00 00 00 01 00 21 FA 41 00 23 05 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 12 00 AB 00", checkPosition.getNomenclatureCode());
        assertEquals(VatTypeEnum.VAT20, checkPosition.getVatType());
    }

    @Test
    void mappingOrderRefundWithError() throws Exception {
        String response = "{" +
                "\"code\": \"RECEIPT_VALIDATION_FAILED\"," +
                "\"message\": \"Чек не прошел валидацию. Причина: не передан объект items\"" +
                "}";
        OrderRefundResponseData orderRefundResponse = new Mapper().mappingToObject(response, OrderRefundResponseData.class);
        assertEquals("RECEIPT_VALIDATION_FAILED", orderRefundResponse.getCode());
        assertNull(orderRefundResponse.getAmount());
        assertEquals("Чек не прошел валидацию. Причина: не передан объект items", orderRefundResponse.getMessage());
        assertNull(orderRefundResponse.getReceipt());
    }
}
