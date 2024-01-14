package com.etiqa;

import com.etiqa.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class EtiqaApplicationTests {

    @Autowired
    private MockMvc mvc;

	@Autowired
	CustomerRepository customerRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test1_customer_findAll() throws Exception {
        this.mvc.perform(get("/customer/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.messageCode", is("S0001")))
                .andExpect(jsonPath("$.description", is("Find All Customer(s)")))
                .andExpect(jsonPath("$.numberOfElements", notNullValue()))
                .andExpect(jsonPath("$.pageNumber", notNullValue()))
                .andExpect(jsonPath("$.pageSize", notNullValue()))
                .andExpect(jsonPath("$.totalPages", notNullValue()))
                .andExpect(jsonPath("$.totalElements", notNullValue()))
                .andExpect(jsonPath("$.customers", notNullValue()));
    }

    @Test
    void test2_product_Exception() throws Exception {
        this.mvc.perform(get("/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"bookTitle\": \"Power Ranger\", \"bookPrice\": \"aa\", \"bookQuantity\": 55 }"))
                .andExpect(status().isInternalServerError()) // 500 status code
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.statusCode").value(500))
                .andExpect(jsonPath("$.messageCode").value("E0001"));
    }



}
