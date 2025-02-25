package hu.neti.autoservice.quote.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteRequest {

    @NotNull
    private Customer customer;

    @NonNull
    @Size(min = 1)
    private List<Part> parts;
}
