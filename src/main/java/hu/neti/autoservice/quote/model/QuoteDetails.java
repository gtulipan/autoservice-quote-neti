package hu.neti.autoservice.quote.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteDetails {
    private double laborFee;
    private double total;
    private double coastOfParts;
    private double discountOfParts;
    private List<Part> partsCalculated;
}
