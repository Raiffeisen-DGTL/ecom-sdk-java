package raiffeisen.ecom.sdk.web;

import raiffeisen.ecom.sdk.exception.MappingException;
import raiffeisen.ecom.sdk.exception.RequestException;
import raiffeisen.ecom.sdk.exception.ValidationException;
import raiffeisen.ecom.sdk.web.query.QueryDescription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebClient {
    public String send(QueryDescription queryDescription) throws RequestException, ValidationException, MappingException {
        try {
            URL url = new URL(queryDescription.getUrlRequest());
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod(queryDescription.getMethodRequest());
            httpConnection.setRequestProperty(QueryDescription.HEAD_CONTENT_TYPE, QueryDescription.HEAD_CONTENT_JSON);
            httpConnection.setRequestProperty(QueryDescription.HEAD_AUTHORIZATION, queryDescription.getAuthorizationHeader());
            httpConnection.setDoOutput(true);
            if (queryDescription.getBodyRequest().isPresent()) {
                String bodyRequest = queryDescription.getBodyRequest().get();
                byte[] bodyRequestByte = bodyRequest.getBytes(StandardCharsets.UTF_8);
                try (OutputStream writer = httpConnection.getOutputStream()) {
                    writer.write(bodyRequestByte);
                }
            }

            try (Reader in = new BufferedReader(
                    new InputStreamReader(httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK
                            ? httpConnection.getInputStream()
                            : httpConnection.getErrorStream(), StandardCharsets.UTF_8.name()))
            ) {
                StringBuilder responseBuilder = new StringBuilder();
                for (int character; (character = in.read()) >= 0; )
                    responseBuilder.append((char) character);
                return responseBuilder.toString();
            }
        } catch (IOException e) {
            throw new RequestException("Ошибка при отправки запроса по URL %s",
                    queryDescription.getMethodRequest(),
                    queryDescription.getUrlRequest()
            );
        }
    }
}
