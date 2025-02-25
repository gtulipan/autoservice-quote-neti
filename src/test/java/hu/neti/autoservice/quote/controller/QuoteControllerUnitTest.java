package hu.neti.autoservice.quote.controller;

import hu.neti.autoservice.quote.data.BuildTestData;
import hu.neti.autoservice.quote.model.CustomerType;
import hu.neti.autoservice.quote.model.QuoteRequest;
import hu.neti.autoservice.quote.model.QuoteResponse;
import hu.neti.autoservice.quote.service.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class QuoteControllerUnitTest {

    @InjectMocks
    private QuoteController quoteController;

    @Mock
    private QuoteService quoteService;

    private QuoteRequest quoteRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        quoteRequest = BuildTestData.buildQuoteRequest(CustomerType.LOYAL);
    }

    @Test
    void testCalculateQuote() {
        var quoteResponse = new QuoteResponse();
        when(quoteService.createQuote(any(), any())).thenReturn(quoteResponse);
        var response = new MockHttpServletResponse();
        var result = quoteController.calculateQuote(quoteRequest);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals(quoteResponse, result.getBody());
    }

    @Test
    void testPrintQuote() {
        var pdfBytes = new byte[]{1, 2, 3};
        when(quoteService.createQuotePdf(any(), any())).thenReturn(pdfBytes);
        var response = new MockHttpServletResponse();
        var result = quoteController.printQuote(quoteRequest);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF_VALUE, response.getContentType());
        assertEquals(pdfBytes, result.getBody());
        assertTrue(result.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION)
                .get(0).startsWith("attachment; filename=\"quote_TesztElek"));
    }
}
