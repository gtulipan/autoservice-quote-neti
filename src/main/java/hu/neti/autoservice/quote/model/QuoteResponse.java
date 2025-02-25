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
public class QuoteResponse {
    private String customerName;
    private String customerType;
    private String total;
    private String laborFee;
    private String coastOfParts;
    private String discountOfParts;
    private List<PartFormatted> partsFormatted;
}
