package raiffeisen.ecom.sdk.utils;

import raiffeisen.ecom.sdk.exception.CryptoException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Класс утилит по работе с шифрованием
 */
public class CryptoUtils {
    /**
     * Кодирование данных по алгоритму HMAC-SHA-256
     *
     * @param secretKey секретный ключ
     * @param data      данные для кодирования
     * @return результат кодирования
     * @throws CryptoException ошибка шифрования
     */
    public static String encodeByHmacSHA256(String secretKey, String data) throws CryptoException {
        final String ALGORITHM = "HmacSHA256";

        try {
            Mac hmacSHA256 = Mac.getInstance(ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8.name()), ALGORITHM);
            hmacSHA256.init(secretKeySpec);

            return byteArrayToHex(hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8.name())));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException("Отсутствует алгоритм шифрования HMAC-SHA-256: %s", e.getMessage());
        } catch (UnsupportedEncodingException | InvalidKeyException e) {
            throw new CryptoException("Проблемы шифрования последовательности %s алгоритмом HMAC-SHA-256: %s", data, e.getMessage());
        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
