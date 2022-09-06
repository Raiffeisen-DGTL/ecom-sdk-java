package raiffeisen.ecom.sdk.service;

import raiffeisen.ecom.sdk.exception.CryptoException;
import raiffeisen.ecom.sdk.utils.CryptoUtils;
import raiffeisen.ecom.sdk.model.PaymentNotice;
import raiffeisen.ecom.sdk.model.transaction.TransactionInfo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис проверки подписи
 */
public class CheckSignatureService {
    /**
     * Серктный ключ
     */
    private final String secretKey;
    /**
     * Идентификатор магазина
     */
    private final String publicId;

    private final DateTimeFormatter dateTimeFormatter;

    public CheckSignatureService(String secretKey, String publicId) {
        this.secretKey = secretKey;
        this.publicId = publicId;
        this.dateTimeFormatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_DATE_TIME)
                .appendOffset("+HH:MM", "+00:00")
                .toFormatter();
    }

    /**
     * Проверка валидности сигнатуры
     *
     * @param signature     проверяемая подпись
     * @param paymentNotice уведомление о платеже
     * @return валидность подписи
     * @throws CryptoException ошибка шифрования
     */
    public boolean checkEventSignature(String signature, PaymentNotice paymentNotice) throws CryptoException {
        String encodeByHmacSHA256 = CryptoUtils.encodeByHmacSHA256(secretKey, createControlLine(paymentNotice.getTransaction()));
        return encodeByHmacSHA256.equals(signature);
    }

    /**
     * Формирование контрольной строки на основе информации о платеже
     *
     * @param transaction информация о платеже
     * @return контрольная строка
     */
    private String createControlLine(TransactionInfo transaction) {
        List<String> listFields = Arrays.asList(
                fieldToString(transaction.getAmount()),
                publicId,
                transaction.getOrderId(),
                transaction.getStatus().getValue().toString(),
                fieldToString(transaction.getStatus().getDate())
        );

        return String.join("|", listFields);
    }

    private String fieldToString(BigDecimal value) {
        if (value == null)
            return "";
        return value.toString();
    }

    private String fieldToString(OffsetDateTime value) {
        if (value == null)
            return "";
        System.out.println(value.format(dateTimeFormatter)+value.toZonedDateTime());
        return value.toString();
    }
}
