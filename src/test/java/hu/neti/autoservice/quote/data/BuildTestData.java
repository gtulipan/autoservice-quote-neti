package hu.neti.autoservice.quote.data;

import hu.neti.autoservice.quote.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildTestData {

    private BuildTestData() {
        super();
    }

    public static String getRequestJson() {
        return """
                {
                  "customer": {
                    "name": "Teszt Elek",
                    "customerType": "LOYAL"
                  },
                  "parts": [
                    {
                      "name": "Főtengely",
                      "purchasePrice": 654600.0
                    },
                    {
                      "name": "Sebességváltó",
                      "purchasePrice": 130578.0
                    }
                  ]
                }""";
    }

    public static QuoteRequest buildQuoteRequest(CustomerType customerType) {
        return QuoteRequest.builder()
                .customer(buildCustomer(customerType))
                .parts(buildParts())
                .build();
    }

    public static Customer buildCustomer(CustomerType customerType) {
        return Customer.builder()
                .customerType(customerType)
                .name("Teszt Elek")
                .build();
    }

    public static List<Part> buildParts() {
        return List.of(
                Part.builder()
                        .name("Főtengely")
                        .purchasePrice(654_600.0)
                        .build(),
                Part.builder()
                        .name("Sebességváltó")
                        .purchasePrice(130_578.0)
                        .build()
        );
    }

    public static QuoteDetails buildQuoteDetails() {
        return QuoteDetails.builder()
                .partsCalculated(buildCalculatedParts())
                .total(1_070_982.792)
                .coastOfParts(835_429.392)
                .laborFee(235_553.40)
                .discountOfParts(43_969.968)
                .build();
    }

    public static List<Part> buildCalculatedParts() {
        return List.of(
                Part.builder()
                        .name("Főtengely")
                        .purchasePrice(733_152.00)
                        .build(),
                Part.builder()
                        .name("Sebességváltó")
                        .purchasePrice(146_247.36)
                        .build()
        );
    }
}
