package hu.neti.autoservice.quote.util;

import hu.neti.autoservice.quote.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static hu.neti.autoservice.quote.constants.Constants.*;
import static hu.neti.autoservice.quote.util.Formatters.getCurrencyFormatter;

@Slf4j
@Component
public class QuoteBuilder {

    public QuoteResponse buildQuoteResponse(Customer customer, QuoteDetails quoteDetails) {
        return QuoteResponse.builder()
                .customerName(customer.getName())
                .customerType(customer.getCustomerType().toString())
                .laborFee(String.format(LABOR_FEE, getCurrencyFormatter().format(quoteDetails.getLaborFee())))
                .total(String.format(TOTAL, getCurrencyFormatter().format(quoteDetails.getTotal())))
                .coastOfParts(String.format(PARTS_PRICES, getCurrencyFormatter().format(quoteDetails.getCoastOfParts())))
                .discountOfParts(String.format(DISCOUNT_ON_PARTS_PRICES, getCurrencyFormatter().format(quoteDetails.getDiscountOfParts())))
                .partsFormatted(buildParts(quoteDetails.getPartsCalculated()))
                .build();
    }

    protected List<PartFormatted> buildParts(List<Part> partsCalculated) {
        return partsCalculated.stream()
                .map(part ->
                        PartFormatted.builder()
                                .name(part.getName())
                                .purchasePrice(getCurrencyFormatter().format(part.getPurchasePrice()))
                                .build()
                )
                .toList();
    }
}
