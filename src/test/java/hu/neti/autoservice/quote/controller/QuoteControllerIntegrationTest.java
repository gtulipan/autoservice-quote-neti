package hu.neti.autoservice.quote.controller;

import hu.neti.autoservice.quote.data.BuildTestData;
import hu.neti.autoservice.quote.model.*;
import hu.neti.autoservice.quote.service.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class QuoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteService quoteService;

    private QuoteRequest quoteRequest;

    @BeforeEach
    public void setUp() {
        quoteRequest = BuildTestData.buildQuoteRequest(CustomerType.LOYAL);
    }

    @Test
    void testCalculateQuote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/quote/calculate-quote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BuildTestData.getRequestJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testPrintQuote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/quote/print-quote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BuildTestData.getRequestJson())
                        .accept(MediaType.APPLICATION_PDF))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }


}
