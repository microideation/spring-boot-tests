package com.microideation.tutorial.springtest.customers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.microideation.tutorial.springtest.domain.Customer;
import com.microideation.tutorial.springtest.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mapper = new ObjectMapper();
    }


    @Test
    public void givenGetCustomer_whenIdIsPresent_thenReturnCustomer() throws Exception {
        // Customer object
        Customer customer = standardCustomer();

        // Mock the get customer
        when(customerService.getCustomer(eq(customer.getId()))).thenReturn(customer);

        // Call the service
        mockMvc.perform(get("/customers/"+customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));


        // Verify the call to the service
        verify(customerService,times(1)).getCustomer(eq(customer.getId()));

    }

    @Test
    public void givenGetCustomer_whenIdIsNotFound_thenReturnErrorStatus() throws Exception {

        // Mock the get customer
        when(customerService.getCustomer(anyLong())).thenThrow(new NoSuchElementException("No customer"));

        // Call the service
        mockMvc.perform(get("/customers/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("failed")));


        // Verify the call to the service
        verify(customerService,times(1)).getCustomer(anyLong());

    }


    @Test
    public void givenRegisterCustomer_whenValidField_thenReturnCustomer() throws Exception {
        // Customer object
        Customer customer = standardCustomer();
        customer.setId(null);

        // JSON
        String cusJson = mapper.writeValueAsString(customer);

        // Mock the get customer
        when(customerService.registerCustomer(eq(customer))).thenReturn(customer);

        // Call the service
        mockMvc.perform(post("/customers/register")
                .content(cusJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));


        // Verify the call to the service
        verify(customerService,times(1)).registerCustomer(eq(customer));

    }


    @Test
    public void givenActivateCustomer_whenValidField_thenReturnCustomerWithActiveStatus() throws Exception {
        // Customer object
        Customer customer = standardCustomer();

        // JSON
        String cusJson = mapper.writeValueAsString(customer);

        // Mock the get customer
        when(customerService.activate(eq(customer.getId()))).thenReturn(customer);

        // Call the service
        mockMvc.perform(put("/customers/activate")
                        .param("custId",customer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));


        // Verify the call to the service
        verify(customerService,times(1)).activate(eq(customer.getId()));

    }


    @Test
    public void givenDeactivateCustomer_whenValidField_thenReturnCustomerWithDisabledStatus() throws Exception {
        // Customer object
        Customer customer = standardCustomer();

        // JSON
        String cusJson = mapper.writeValueAsString(customer);

        // Mock the get customer
        when(customerService.deactivate(eq(customer.getId()))).thenReturn(customer);

        // Call the service
        mockMvc.perform(put("/customers/deactivate")
                        .param("custId",customer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is(customer.getFirstname())));


        // Verify the call to the service
        verify(customerService,times(1)).deactivate(eq(customer.getId()));

    }

}
