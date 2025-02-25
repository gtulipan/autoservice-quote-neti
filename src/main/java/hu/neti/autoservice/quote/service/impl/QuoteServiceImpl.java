package hu.neti.autoservice.quote.service.impl;

import hu.neti.autoservice.quote.model.Customer;
import hu.neti.autoservice.quote.model.Part;
import hu.neti.autoservice.quote.model.QuoteDetails;
import hu.neti.autoservice.quote.model.QuoteResponse;
import hu.neti.autoservice.quote.service.QuoteService;
import hu.neti.autoservice.quote.util.QuoteBuilder;
import hu.neti.autoservice.quote.util.QuoteCalculator;
import hu.neti.autoservice.quote.util.QuotePdfBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteCalculator quoteCalculator;
    private final QuoteBuilder quoteBuilder;
    private final QuotePdfBuilder quotePdfBuilder;

    @Override
    public QuoteResponse createQuote(Customer customer, List<Part> parts) {
        log.debug("Customer in createQuote() method: {}, parts: {}", customer, parts);
        QuoteDetails quoteDetails = quoteCalculator.calculateQuote(customer, parts);
        QuoteResponse response = quoteBuilder.buildQuoteResponse(customer, quoteDetails);
        log.debug("Response quote is: {}", response);
        return response;
    }

    @Override
    public byte[] createQuotePdf(Customer customer, List<Part> parts) {
        log.debug("Customer in generateQuotePdf() method: {}, parts: {}", customer, parts);
        QuoteDetails quoteDetails = quoteCalculator.calculateQuote(customer, parts);
        return quotePdfBuilder.generateQuotePdf(customer, quoteDetails);
    }
}
