package lab;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Record components carry constraints directly; they apply to the accessor and constructor. */
public record Quantity(@NotNull @Sku String sku, @Min(1) int amount) {}
