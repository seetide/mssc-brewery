package com.haitao.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haitao.msscbrewery.services.CustomerService;
import com.haitao.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto validCustomer;

    @Before
    public void setUp()  {
        validCustomer = CustomerDto.builder().id(UUID.randomUUID())
                .name("Bill Gate")
                .build();
    }

    @Test
    public void getCustomer() throws Exception{
        given(customerService.getCustomerById(any(UUID.class))).willReturn(validCustomer);

        mockMvc.perform(get("/api/v1/customer/" + validCustomer.getId().toString()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is("Bill Gate")));
    }

    @Test
    public void handlePost() throws Exception{
        //given
        CustomerDto customerDto = validCustomer;
        customerDto.setId(null);
        CustomerDto savedDto = CustomerDto.builder().id(UUID.randomUUID()).name("New Customer").build();
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        given(customerService.saveNewCustomer(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/customer/")
                .contentType(APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void handleUpdate() throws Exception{
        //given
        CustomerDto customerDto = validCustomer;
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        //when
        mockMvc.perform(put("/api/v1/customer/" + validCustomer.getId().toString())
                .contentType(APPLICATION_JSON)
                .content(customerDtoJson)
        )
                .andExpect(status().isNoContent());

        then(customerService).should().updateCustomer(any(), any());
    }

//    @Test
//    public void deleteCustomer() {
//    }
}