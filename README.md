# Ecommerce payment API SDK

SDK модуль для внедрения эквайринга Райффайзенбанка.

## Установка и подключение

Требования:

- Java 8+
- Apache Maven

Для подключения SDK требуется:

- Создать в корне проекта каталог с названием "dependencies".
- Поместить в созданный каталог файл .jar и [pom.xml по ссылке](/docs/dependencies/pom.xml).
- pom.xml __своего__ проекта поместить следующее:

~~~
    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>raiffeisen</groupId>
            <artifactId>ecom-sdk-java</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-install-plugin</artifactId>
                <version>2.5.2</version>
                <configuration>
                    <groupId>raiffeisen</groupId>
                    <artifactId>ecom-sdk-java</artifactId>
                    <version>1.0.0</version>
                    <packaging>jar</packaging>
                    <file>dependencies/ecom-sdk-java-1.0.0.jar</file>
                    <generatePom>false</generatePom>
                    <pomFile>dependencies/pom.xml</pomFile>
                </configuration>
                <executions>
                    <execution>
                        <id>install-jar-lib</id>
                        <goals>
                            <goal>install-file</goal>
                        </goals>
                        <phase>validate</phase>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
~~~

- Выполнить команды в maven:
    + validate(`mvn validate`)
    + install(`mvn install`)

## Документация

**Raiffeisenbank e-commerce API: https://pay.raif.ru/doc/ecom.html#tag/js-sdk

## Клиент API

Для использования SDK требуется секретный ключ `secretKey`, подробности
<a href="https://pay.raif.ru/doc/ecom.html#section/API/Avtorizaciya">в документации</a>
и
<a href="https://www.raiffeisen.ru/corporate/management/commerce/">сайте банка</a>.

~~~ java
String secretKey = "***";

EcomClient client = new EcomClient(secretKey);
~~~

Параметры конструктора и свойства клиента:

- `host` - хост api, по умолчанию https://e-commerce.raiffeisen.ru, доступ на чтение и запись;
- `secretKey` - секретный ключ, обязательный, доступ только на запись;

Клиент может вернуть следующие типы исключений:

- `RequestException` - ошибка сетевого взаимодействия
- `MappingException` - ошибки при маппирование данных
- `CryptoException` - ошибка шифрования данных
- `ValidationException` - ошибка валидации данных

## Примеры

### Получение информации о статусе транзакции

Метод `getTransaction` возвращает информацию о статусе транзакции.
В параметрах нужно указать:

- `orderId` - идентификатор заказа.

~~~ java
String orderId = "testOrder";

TransactionResponseData response = client.getTransaction(secretKey);
~~~

Ответ:

~~~
{
  "code": "SUCCESS",
  "transaction": {
    "id": 120059,
    "orderId": "testOrder",
    "status": {
      "value": "SUCCESS",
      "date": "2019-07-11T17:45:13+03:00"
    },
    "paymentMethod": "acquiring",
    "paymentParams": {
      "rrn": 935014591810,
      "authCode": 25984
    },
    "amount": 12500.5,
    "comment": "Покупка шоколадного торта",
    "extra": {
      "additionalInfo": "Sweet Cake"
    }
  }
}
~~~

### Оформление возврата по платежу

Метод `createRefund` создает возврат по заказу.
В параметрах нужно указать:

- `orderId` - идентификатор заказа;
- `refundId` - идентификатор заказа;
- `refund` - экземпляр класса `Refund`;

~~~ java
String orderId = "testOrder";
String refundId = "testRefund";
Refund refund = new Refund(BigDecimal.valueOf(150));

OrderRefundResponseData response = client.createRefund(orderId, refundId, refund);
~~~

Ответ:

~~~
{
  "code": "SUCCESS",
  "amount": 150,
  "refundStatus": "IN_PROGRESS"
}
~~~

### Статус возврата

Метод `getRefundInfo` создает возврат по заказу.
В параметрах нужно указать:

- `orderId` - идентификатор заказа;
- `refundId` - идентификатор заказа;

~~~ java
String orderId = "testOrder";
String refundId = "testRefund";

OrderRefundResponseData response = client.getRefundInfo(orderId, refundId);
~~~

### Парсинг уведомления

Метод `parsingPaymentNotice` преобразует JSON-ответ в экземпляр класса `PaymentNotice`.
В параметрах нужно указать:

- `jsonPaymentNotice` - тело ответа;

~~~ java
String json = "{\"event\": \"payment\",\"transaction\":{\"id\": 120059,}, ...}";

PaymentNotice response = client.parsingPaymentNotice(orderId, json);
~~~

### Обработка уведомлений

Метод `checkEventSignature` проверяет подпись уведомления о платеже.
В параметрах нужно указать:

- `signature` - содержимое заголовка x-api-signature-sha256;
- `eventBody` - разобранный JSON из тела запроса преобразованный в экземпляр класса `PaymentNotice`;
- `publicId` - идентификатор мерчанта.

~~~ java
String json = "{\"event\": \"payment\",\"transaction\":{\"id\": 120059,}, ...}";
PaymentNotice response = client.parsingPaymentNotice(orderId, json);

String signature = "***";
String publicId = "***";

client.checkEventSignature(signature, eventBody, publicId); // true or false
~~~