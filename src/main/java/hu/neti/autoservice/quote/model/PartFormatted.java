package hu.neti.autoservice.quote.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartFormatted {
    private String name;
    private String purchasePrice;
}
