package lab;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderLine {
    private String sku;
    private int quantity;
    private BigDecimal unitPrice;
}
