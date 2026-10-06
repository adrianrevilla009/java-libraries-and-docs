package lab;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class OrdersDocsTest {
    @Autowired MockMvc mvc;

    @Test
    void createOrderIsDocumented() throws Exception {
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sku\":\"ABC-1234\",\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andDo(document("create-order",
                        requestFields(
                                fieldWithPath("sku").description("Product SKU"),
                                fieldWithPath("quantity").description("Units ordered")),
                        responseFields(
                                fieldWithPath("id").description("Order id"),
                                fieldWithPath("sku").description("Product SKU"),
                                fieldWithPath("quantity").description("Units ordered"),
                                fieldWithPath("total").description("Price times quantity"))));
        // the test fails if the docs drift from the real response (undocumented field = error)
        assertTrue(Files.exists(Path.of("target/generated-snippets/create-order/http-response.adoc")));
    }

    @Test
    void getOrderIsDocumented() throws Exception {
        mvc.perform(get("/orders/{id}", 5))
                .andExpect(status().isOk())
                .andDo(document("get-order",
                        pathParameters(parameterWithName("id").description("Order id")),
                        responseFields(
                                fieldWithPath("id").description("Order id"),
                                fieldWithPath("sku").description("Product SKU"),
                                fieldWithPath("quantity").description("Units ordered"),
                                fieldWithPath("total").description("Price times quantity"))));
        assertTrue(Files.exists(Path.of("target/generated-snippets/get-order/path-parameters.adoc")));
    }
}
