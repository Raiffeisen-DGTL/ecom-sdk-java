package raiffeisen.ecom.sdk.web.query;

import raiffeisen.ecom.sdk.exception.MappingException;
import raiffeisen.ecom.sdk.exception.ValidationException;

import java.util.Optional;

/**
 * Описание запроса
 */
public interface QueryDescription {
    String HEAD_AUTHORIZATION = "Authorization";
    String HEAD_CONTENT_TYPE = "Content-Type";
    String HEAD_CONTENT_JSON = "application/json";
    String BEARER = "Bearer ";

    String METHOD_GET = "GET";
    String METHOD_POST = "POST";

    /**
     * Возвращает тело запрос в формате JSON
     *
     * @return тело запроса
     * @throws MappingException    ошибка маппинга данных
     * @throws ValidationException ошибка валидации данных
     */
    Optional<String> getBodyRequest() throws MappingException, ValidationException;

    /**
     * Возвращает метод запроса (GET / POST)
     *
     * @return метод запроса
     */
    String getMethodRequest();

    /**
     * Возвращает URL запроса
     *
     * @return URL запроса
     */
    String getUrlRequest();

    /**
     * Возвращает значение параметра заголовка запроса 'Authorization'
     *
     * @return значение параметра 'Authorization'
     */
    String getAuthorizationHeader();
}
