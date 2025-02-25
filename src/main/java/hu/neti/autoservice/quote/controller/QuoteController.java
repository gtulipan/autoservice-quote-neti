package hu.neti.autoservice.quote.controller;

import hu.neti.autoservice.quote.model.QuoteRequest;
import hu.neti.autoservice.quote.model.QuoteResponse;
import hu.neti.autoservice.quote.service.QuoteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hu.neti.autoservice.quote.util.Formatters.getFileName;

@Slf4j
@OpenAPIDefinition(info = @Info(title = "Quote Service", version = "v1"))
@RequiredArgsConstructor
@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService quoteService;

    @Operation(summary = "POST request to calculate quote.", description = "Car service custom quote maker application.")
    @PostMapping(value = "/calculate-quote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuoteResponse> calculateQuote(@Validated @RequestBody QuoteRequest quoteRequest) {
        log.debug("******** calculateQuote() called ********");
        log.debug("Parameter value is in the calculateQuote() method: {}", quoteRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-Type", "application/json; charset=UTF-8");

        return ResponseEntity.ok(quoteService.createQuote(quoteRequest.getCustomer(), quoteRequest.getParts()));
    }

    @Operation(summary = "POST request to get the quote as PDF.", description = "Generate a PDF with the custom quote.")
    @PostMapping("/print-quote")
    public ResponseEntity<byte[]> printQuote(@Validated @RequestBody QuoteRequest quoteRequest) {
        log.debug("******** printQuote() called ********");
        log.debug("Parameter value is in the printQuote() method: {}", quoteRequest);
        byte[] pdfBytes = quoteService.createQuotePdf(quoteRequest.getCustomer(), quoteRequest.getParts());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(getFileName(quoteRequest.getCustomer().getName())).build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
