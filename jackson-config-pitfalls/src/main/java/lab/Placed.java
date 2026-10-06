package lab;

import java.time.Instant;

/** An order event; the timestamp is where Jackson's defaults bite. */
public record Placed(String orderId, Instant at) {}
