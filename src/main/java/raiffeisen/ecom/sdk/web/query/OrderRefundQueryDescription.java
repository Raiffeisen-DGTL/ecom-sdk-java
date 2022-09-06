package raiffeisen.ecom.sdk.web.query;

import raiffeisen.ecom.sdk.exception.MappingException;
import raiffeisen.ecom.sdk.exception.ValidationException;
import raiffeisen.ecom.sdk.mapper.Mapper;
import raiffeisen.ecom.sdk.model.refund.CheckPosition;
import raiffeisen.ecom.sdk.model.refund.PaymentModeEnum;
import raiffeisen.ecom.sdk.model.refund.Receipt;
import raiffeisen.ecom.sdk.model.refund.ReceiptCustomer;
import raiffeisen.ecom.sdk.model.refund.Refund;
import raiffeisen.ecom.sdk.model.refund.SupplierInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderRefundQueryDescription extends AbstractQueryDescription {
    private final String orderId;
    private final String refundId;
    private final Refund refund;
    private final Mapper mapper;

    public OrderRefundQueryDescription(String domain,
                                       String secretKey,
                                       String orderId,
                                       String refundId,
                                       Refund refund) {
        super(domain, secretKey);
        this.orderId = orderId;
        this.refundId = refundId;
        this.refund = refund;

        this.mapper = new Mapper();
    }

    @Override
    public Optional<String> getBodyRequest() throws MappingException, ValidationException {
        if (refund == null)
            return Optional.empty();
        validateRequestObject();
        return Optional.of(mapper.mappingToJson(refund));
    }

    @Override
    public String getMethodRequest() {
        return METHOD_POST;
    }

    @Override
    public String getUrlRequest() {
        String URL = "/payments/v1/orders/%s/refunds/%s";
        return createUri(String.format(URL, orderId, refundId));
    }

    private void validateRequestObject() throws ValidationException {
        final String ERROR_MESSAGE = "При оформление возврата поле \"%s\" должно быть заполнено";
        if (refund.getAmount() == null)
            throw new ValidationException(ERROR_MESSAGE, "Сумма возврата в рублях");
        Receipt receipt = refund.getReceipt();
        if (receipt == null)
            return;
        ReceiptCustomer customer = receipt.getCustomer();
        if (customer != null && isEmpty(customer.getEmail()))
            throw new ValidationException(ERROR_MESSAGE, "E-mail покупателя для отправки чека");
        List<CheckPosition> items = receipt.getItems();
        if (items == null || items.isEmpty())
            throw new ValidationException(ERROR_MESSAGE, "Позиции чека");
        for (CheckPosition item : items) {
            if (isEmpty(item.getName()))
                throw new ValidationException(ERROR_MESSAGE, "Позиции чека. Наименование товара, работы, услуги, иного предмета расчета");
            if (item.getPrice() == null)
                throw new ValidationException(ERROR_MESSAGE, "Позиции чека. Цена за единицу товара, работы, услуги, иного предмета расчета в рублях");
            BigDecimal price = item.getPrice() == null ? BigDecimal.ZERO : item.getPrice();
            BigDecimal quantity = item.getQuantity() == null ? BigDecimal.ZERO : item.getQuantity();
            BigDecimal sum = price.multiply(quantity);
            if (sum.compareTo(item.getAmount()) != 0)
                throw new ValidationException("Произведение цены на количество не соответствует значению поля \"Сумма в рублях\"");
            if (item.getVatType() == null)
                throw new ValidationException(ERROR_MESSAGE, "Позиции чека. Ставка НДС на позицию чека");
            SupplierInfo supplierInfo = item.getSupplierInfo();
            if (supplierInfo != null && isEmpty(supplierInfo.getInn())) {
                throw new ValidationException(ERROR_MESSAGE, "Позиции чека. Данные о поставщике. ИНН поставщика");
            }

            if (item.getPaymentMode() == null)
                item.setPaymentMode(PaymentModeEnum.FULL_PREPAYMENT);
        }
    }

    private boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }
}
