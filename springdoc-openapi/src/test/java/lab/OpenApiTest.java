package lab;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiTest {
    @Autowired MockMvc mvc;

    @Test
    void specIsGeneratedFromCode() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Orders API"))
                .andExpect(jsonPath("$.paths['/orders'].post.summary").value("Place an order"))
                .andExpect(jsonPath("$.paths['/orders/{id}'].get.summary").value("Fetch an order by id"));
    }

    @Test
    void beanValidationShowsUpInSchema() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(jsonPath("$.components.schemas.OrderRequest.required[0]").value("sku"))
                .andExpect(jsonPath("$.components.schemas.OrderRequest.properties.quantity.minimum").value(1))
                .andExpect(jsonPath("$.components.schemas.OrderRequest.properties.sku.example").value("ABC-1234"));
    }

    @Test
    void swaggerUiIsServed() throws Exception {
        mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
    }
}
