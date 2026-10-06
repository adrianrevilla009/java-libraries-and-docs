package lab;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/** Polymorphism needs an explicit allow-list of subtypes; never use default typing on untrusted input. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Payment.Card.class, name = "card"),
    @JsonSubTypes.Type(value = Payment.Invoice.class, name = "invoice")
})
public sealed interface Payment {
    record Card(String last4) implements Payment {}

    record Invoice(int dueDays) implements Payment {}
}
