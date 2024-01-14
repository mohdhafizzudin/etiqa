package com.etiqa;

import com.etiqa.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EtiqaApplicationTests {

	@Autowired
	private MockMvc mvc;

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
				.andExpect(jsonPath("$.numberOfElements", is(3)))
				.andExpect(jsonPath("$.pageNumber", is(1)))
				.andExpect(jsonPath("$.pageSize", is(20)))
				.andExpect(jsonPath("$.totalPages", is(1)))
				.andExpect(jsonPath("$.totalElements", is(3)))
				.andExpect(jsonPath("$.customers", hasSize(3)));
	}

}
