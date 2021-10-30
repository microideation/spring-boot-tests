package com.microideation.tutorial.springtest.customers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.microideation.tutorial.springtest.dictionary.CustomerStatus;
import com.microideation.tutorial.springtest.domain.Customer;
import com.microideation.tutorial.springtest.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static com.microideation.tutorial.springtest.customers.CustomerFixture.standardCustomer;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void givenGetCustomer_whenIdIsPresent_thenReturnCustomer() throws Exception {
        // Customer object
        Customer customer = standardCustomer();
        customer.setId(null);

        // Save
        customer = customerService.registerCustomer(customer);

        // Call the service
        mockMvc.perform(get("/customers/"+customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));


    }

    @Test
    public void givenGetCustomer_whenIdIsNotFound_thenReturnErrorStatus() throws Exception {

        // Call the service
        mockMvc.perform(get("/customers/10000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("failed")));


    }


    @Test
    public void givenRegisterCustomer_whenValidField_thenReturnCustomer() throws Exception {
        // Customer object
        Customer customer = standardCustomer();
        customer.setId(null);

        // JSON
        String cusJson = mapper.writeValueAsString(customer);

        // Call the service
        mockMvc.perform(post("/customers/register")
                .content(cusJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));

    }


    @Test
    public void givenActivateCustomer_whenValidField_thenReturnCustomerWithActiveStatus() throws Exception {
        // Customer object
        Customer customer = standardCustomer();
        customer.setId(null);

        // Save
        customer = customerService.registerCustomer(customer);

        // Call the service
        mockMvc.perform(put("/customers/activate")
                        .param("custId",customer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(CustomerStatus.ACTIVE.name())))
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));



    }


    @Test
    public void givenDeactivateCustomer_whenValidField_thenReturnCustomerWithDisabledStatus() throws Exception {
        // Customer object
        Customer customer = standardCustomer();
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setId(null);

        // Save
        customer = customerService.registerCustomer(customer);

        // Call the service
        mockMvc.perform(put("/customers/deactivate")
                        .param("custId",customer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(CustomerStatus.DISABLED.name())))
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));

    }

}
