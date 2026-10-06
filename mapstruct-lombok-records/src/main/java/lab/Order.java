package lab;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/** Mutable JPA-style entity: Lombok generates getters/setters. */
@Data
public class Order {
    private Long id;
    private String customerEmail;
    private List<OrderLine> lines;
    private BigDecimal internalCost; // must never leak to the API
}
