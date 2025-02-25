package hu.neti.autoservice.quote.service.impl;

import hu.neti.autoservice.quote.data.BuildTestData;
import hu.neti.autoservice.quote.model.*;
import hu.neti.autoservice.quote.util.QuoteBuilder;
import hu.neti.autoservice.quote.util.QuoteCalculator;
import hu.neti.autoservice.quote.util.QuotePdfBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class QuoteServiceImplTest {

    @InjectMocks
    private QuoteServiceImpl quoteService;

    @Mock
    private QuoteCalculator quoteCalculator;

    @Mock
    private QuoteBuilder quoteBuilder;

    @Mock
    private QuotePdfBuilder quotePdfBuilder;

    private Customer customer;
    private List<Part> parts;
    private QuoteDetails quoteDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = BuildTestData.buildCustomer(CustomerType.LOYAL);
        parts = BuildTestData.buildParts();
        quoteDetails = BuildTestData.buildQuoteDetails();
    }

    @Test
    void testCreateQuote() {
        QuoteResponse expectedResponse = new QuoteResponse();
        when(quoteCalculator.calculateQuote(any(), any())).thenReturn(quoteDetails);
        when(quoteBuilder.buildQuoteResponse(any(), any())).thenReturn(expectedResponse);

        QuoteResponse actualResponse = quoteService.createQuote(customer, parts);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testCreateQuotePdf() {
        var expectedPdf = new byte[]{1, 2, 3};
        when(quoteCalculator.calculateQuote(any(), any())).thenReturn(quoteDetails);
        when(quotePdfBuilder.generateQuotePdf(any(), any())).thenReturn(expectedPdf);
        var actualPdf = quoteService.createQuotePdf(customer, parts);

        assertArrayEquals(expectedPdf, actualPdf);
    }
}