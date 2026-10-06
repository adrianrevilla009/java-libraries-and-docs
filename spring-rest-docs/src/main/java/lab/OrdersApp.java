package lab;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class OrdersApp {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApp.class, args);
    }

    public record OrderRequest(String sku, int quantity) {}

    public record OrderView(long id, String sku, int quantity, BigDecimal total) {}

    @RestController
    static class OrderController {
        private static final Map<String, BigDecimal> PRICES = Map.of("ABC-1234", new BigDecimal("2.50"));

        @PostMapping("/orders")
        @ResponseStatus(HttpStatus.CREATED)
        OrderView create(@RequestBody OrderRequest req) {
            return view(1L, req.sku(), req.quantity());
        }

        @GetMapping("/orders/{id}")
        OrderView get(@PathVariable long id) {
            return view(id, "ABC-1234", 2);
        }

        private OrderView view(long id, String sku, int qty) {
            var price = PRICES.getOrDefault(sku, BigDecimal.ONE);
            return new OrderView(id, sku, qty, price.multiply(BigDecimal.valueOf(qty)));
        }
    }
}
