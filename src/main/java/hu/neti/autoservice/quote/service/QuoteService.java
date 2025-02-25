package hu.neti.autoservice.quote.service;

import hu.neti.autoservice.quote.model.Customer;
import hu.neti.autoservice.quote.model.Part;
import hu.neti.autoservice.quote.model.QuoteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Service to calculate quote</p>
 * */
@Service
public interface QuoteService {

    /**
     * @param customer : The Customer object contains the name of the car owner and the type of owner (`REGULAR`,`LOYAL`, `VIP`).
     * @param parts : List of parts and their costs.
     * @return A {@link QuoteResponse} object containing the quote details.
     * */
    QuoteResponse createQuote(Customer customer, List<Part> parts);

    /**
     * @param customer : The Customer object contains the name of the car owner and the type of owner (`REGULAR`,`LOYAL`, `VIP`).
     * @param parts : List of parts and their costs.
     * @return A PDF file containing the details of the offer.
     * */
    byte[] createQuotePdf(Customer customer, List<Part> parts);
}
