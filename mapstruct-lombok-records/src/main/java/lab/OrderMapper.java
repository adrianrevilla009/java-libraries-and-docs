package lab;

import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

// unmappedTargetPolicy=ERROR makes a forgotten field a compile error
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customerEmail", target = "email")
    @Mapping(target = "total", expression = "java(total(order.getLines()))")
    OrderDto toDto(Order order);

    OrderDto.LineDto toDto(OrderLine line);

    // records have no setters: MapStruct uses the canonical constructor
    @Mapping(source = "email", target = "customerEmail")
    @Mapping(target = "internalCost", ignore = true)
    Order toEntity(OrderDto dto);

    OrderLine toEntity(OrderDto.LineDto dto);

    default BigDecimal total(List<OrderLine> lines) {
        return lines == null ? BigDecimal.ZERO : lines.stream()
                .map(l -> l.getUnitPrice().multiply(BigDecimal.valueOf(l.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
