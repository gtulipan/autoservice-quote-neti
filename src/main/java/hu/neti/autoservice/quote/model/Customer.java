package hu.neti.autoservice.quote.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @NotBlank
    String name;

    @NonNull
    CustomerType customerType;
}
