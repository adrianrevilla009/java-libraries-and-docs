package lab;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class OrdersApp {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApp.class, args);
    }

    @Bean
    OpenAPI api() {
        return new OpenAPI().info(new Info().title("Orders API").version("1.0.0"));
    }

    @Schema(description = "A request to buy a product")
    public record OrderRequest(
            @Schema(example = "ABC-1234") @NotBlank String sku,
            @Min(1) int quantity) {}

    public record OrderView(long id, String sku, int quantity) {}

    @RestController
    static class OrderController {
        @Operation(summary = "Place an order")
        @ApiResponse(responseCode = "200", description = "Order accepted")
        @PostMapping("/orders")
        OrderView create(@Valid @RequestBody OrderRequest req) {
            return new OrderView(1L, req.sku(), req.quantity());
        }

        @Operation(summary = "Fetch an order by id")
        @GetMapping("/orders/{id}")
        OrderView get(@PathVariable long id) {
            return new OrderView(id, "ABC-1234", 1);
        }
    }
}
