package raiffeisen.ecom.sdk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static final String TEST_URL;
    public static final String PRODUCTION_URL;

    static {
        Properties properties = new Properties();

        try (InputStream propertiesFile = PropertiesLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(propertiesFile);
        } catch (NullPointerException | IOException e) {
            System.err.println("Cannot load configuration file. Loading default values.");
        }

        TEST_URL = properties.getProperty("url.test", "https://test.ecom.raiffeisen.ru");
        PRODUCTION_URL = properties.getProperty("url.production", "https://e-commerce.raiffeisen.ru");
    }
}
