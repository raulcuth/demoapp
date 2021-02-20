package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.demo.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author raulcuth
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("int_test")
public class ProductControllerITest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void should_save_product() throws Exception {
        Product returnedProduct = new Product();
        returnedProduct.setName("prodtest");
        returnedProduct.setQuantity(2);
        returnedProduct.setPrice(20d);
        returnedProduct.setDescription("description");
        mockMvc.perform(post("/product")
                .content(objectMapper.writeValueAsString(returnedProduct))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("prodtest"));
    }

    @Test
    public void should_fail_save_product_bad_request() throws Exception {
        Product returnedProduct = new Product();
        returnedProduct.setName("prodtest");
        returnedProduct.setQuantity(-3);
        returnedProduct.setPrice(20d);
        returnedProduct.setDescription("description");
        mockMvc.perform(post("/product")
                .content(objectMapper.writeValueAsString(returnedProduct))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_code").value("ERR000001"))
                .andExpect(jsonPath("$.message").value("Invalid input provided."))
                .andExpect(
                        jsonPath("$.description").value("field [quantity] is invalid. \nPlease provide valid data."));
    }
}
