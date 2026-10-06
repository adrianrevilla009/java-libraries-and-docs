package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrderMapperTest {
    private Order order() {
        var line = new OrderLine();
        line.setSku("SKU-1");
        line.setQuantity(3);
        line.setUnitPrice(new BigDecimal("2.50"));
        var o = new Order();
        o.setId(7L);
        o.setCustomerEmail("a@b.c");
        o.setLines(List.of(line));
        o.setInternalCost(new BigDecimal("1.00"));
        return o;
    }

    @Test
    void entityToRecordRenamesAndComputesTotal() {
        OrderDto dto = OrderMapper.INSTANCE.toDto(order());
        assertEquals("a@b.c", dto.email());
        assertEquals(new BigDecimal("7.50"), dto.total());
        assertEquals("SKU-1", dto.lines().get(0).sku());
    }

    @Test
    void recordToEntityUsesLombokSettersAndDropsInternalCost() {
        Order back = OrderMapper.INSTANCE.toEntity(OrderMapper.INSTANCE.toDto(order()));
        assertEquals("a@b.c", back.getCustomerEmail());
        assertEquals(3, back.getLines().get(0).getQuantity());
        assertNull(back.getInternalCost());
    }
}
