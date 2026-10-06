package lab;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class Json {
    private Json() {}

    /** A bare mapper: no java.time support, unknown properties are fatal. */
    public static ObjectMapper naive() {
        return new ObjectMapper();
    }

    /** What a service should use: ISO-8601 dates and tolerant readers. */
    public static ObjectMapper configured() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
