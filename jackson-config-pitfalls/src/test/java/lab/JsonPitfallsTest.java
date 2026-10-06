package lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class JsonPitfallsTest {
    private final Placed placed = new Placed("o-1", Instant.parse("2026-01-02T03:04:05Z"));

    @Test
    void pitfall1_naiveMapperRefusesJavaTime() {
        assertThrows(InvalidDefinitionException.class, () -> Json.naive().writeValueAsString(placed));
    }

    @Test
    void javaTimeModuleWritesIsoStringNotEpochNumber() throws Exception {
        assertEquals("{\"orderId\":\"o-1\",\"at\":\"2026-01-02T03:04:05Z\"}",
                Json.configured().writeValueAsString(placed));
    }

    @Test
    void pitfall2_unknownPropertiesFailByDefault() throws Exception {
        String in = "{\"orderId\":\"o-1\",\"at\":\"2026-01-02T03:04:05Z\",\"extra\":1}";
        assertThrows(UnrecognizedPropertyException.class, () ->
                Json.naive().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
                        .readValue(in, Placed.class));
        assertEquals(placed, Json.configured().readValue(in, Placed.class));
    }

    @Test
    void pitfall3_polymorphismRoundTripsByTypeName() throws Exception {
        var mapper = Json.configured();
        String json = mapper.writeValueAsString(new Payment.Card("4242"));
        assertEquals("{\"type\":\"card\",\"last4\":\"4242\"}", json);
        assertInstanceOf(Payment.Card.class, mapper.readValue(json, Payment.class));
    }

    @Test
    void unlistedSubtypeIsRejected() {
        assertThrows(InvalidTypeIdException.class, () ->
                Json.configured().readValue("{\"type\":\"crypto\"}", Payment.class));
    }
}
