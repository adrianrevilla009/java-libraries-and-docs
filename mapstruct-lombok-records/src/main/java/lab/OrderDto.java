package lab;

import java.math.BigDecimal;
import java.util.List;

/** Immutable API view: records need no Lombok. */
public record OrderDto(Long id, String email, List<LineDto> lines, BigDecimal total) {
    public record LineDto(String sku, int quantity, BigDecimal unitPrice) {}
}
