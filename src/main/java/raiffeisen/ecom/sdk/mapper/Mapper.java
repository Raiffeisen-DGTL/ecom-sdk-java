package raiffeisen.ecom.sdk.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import raiffeisen.ecom.sdk.exception.MappingException;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
    private final ObjectMapper objectMapper;

    public Mapper() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return OffsetDateTime.parse(jsonParser.getText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            }
        });
        objectMapper.registerModule(simpleModule);
    }

    public <T> T mappingToObject(String json, Class<T> responseDataClass) throws MappingException {
        try {
            return objectMapper.readValue(json, responseDataClass);
        } catch (JsonProcessingException e) {
            throw new MappingException("При маппинге ответа произошла ошибка: %s\n%s", e.getMessage(), json);
        }
    }

    public String mappingToJson(Object requestBody) throws MappingException {
        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new MappingException("При преобразование объекта %s в JSON произошла ошибка: %s", requestBody.getClass().getName(), e.getMessage());
        }
    }
}
