package com.microideation.tutorial.springtest.customers;

import com.microideation.tutorial.springtest.dictionary.CustomerStatus;
import com.microideation.tutorial.springtest.domain.Customer;
import com.microideation.tutorial.springtest.repository.CustomerRepository;
import com.microideation.tutorial.springtest.service.CustomerService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.microideation.tutorial.springtest.customers.CustomerFixture.standardCustomer;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        customerService = new CustomerService(customerRepository);
    }


    @Test
    public void givenGetCustomer_whenFound_thenReturnCustomer() {

        // Create the objet
        Customer customer = standardCustomer();

        // Pass to mock
        when(customerRepository.findById(eq(customer.getId()))).thenReturn(Optional.of(customer));

        // Call
        Customer result = customerService.getCustomer(customer.getId());

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(),customer.getId());

        // Verify call to mock
        verify(customerRepository,times(1)).findById(eq(customer.getId()));
    }

    @Test
    public void givenGetCustomer_whenNotFound_thenThrowNoSuchElementException() {

        // Set mock to return null
        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        // Assert exception
        Assertions.assertThrows(NoSuchElementException.class,() -> {
            customerService.getCustomer(10l);
        });

        // Verify call to mock
        verify(customerRepository,times(1)).findById(anyLong());
    }


    @Test
    public void givenRegisterCustomer_whenInValid_thenThrowException() {

        Customer customer = standardCustomer();
        customer.setFirstname(null);

        // Assert exception
        Assertions.assertThrows(RuntimeException.class,() -> {
            customerService.registerCustomer(customer);
        });

        // Verify that the save was not called
        verify(customerRepository,times(0)).findById(anyLong());
    }

    @Test
    public void givenRegisterCustomer_whenValid_thenRegisterAndReturn() {

        // Create customer
        Customer customer = standardCustomer();
        customer.setId(null);

        // Saved object
        Customer savedCustomer = standardCustomer();

        // Mock the save call
        when(customerRepository.save(eq(customer))).thenReturn(savedCustomer);

        // Call
        Customer saved = customerService.registerCustomer(customer);

        // Assertions
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(saved.getStatus(), CustomerStatus.DISABLED);

        // Verify that the save was not called
        verify(customerRepository,times(1)).save(eq(customer));
    }


    @Test
    public void givenActivate_whenSuccess_thenStatusShowActive() {

        // Build the customer
        Customer customer = standardCustomer();

        // Return the customer object for id
        when(customerRepository.findById(eq(customer.getId()))).thenReturn(Optional.of(customer));
        // Return the same object that is passed to the mock
        when(customerRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // Call
        Customer updCustomer = customerService.activate(customer.getId());

        // Assertions
        Assertions.assertNotNull(updCustomer);
        Assertions.assertEquals(updCustomer.getStatus(),CustomerStatus.ACTIVE);

        // Verify that the mock methods called
        verify(customerRepository,times(1)).findById(eq(customer.getId()));
        verify(customerRepository,times(1)).save(any());
    }


    @Test
    public void givenDeactivate_whenSuccess_thenStatusShowDisabled() {

        // Build the customer
        Customer customer = standardCustomer();

        // Return the customer object for id
        when(customerRepository.findById(eq(customer.getId()))).thenReturn(Optional.of(customer));
        // Return the same object that is passed to the mock
        when(customerRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // Call
        Customer updCustomer = customerService.deactivate(customer.getId());

        // Assertions
        Assertions.assertNotNull(updCustomer);
        Assertions.assertEquals(updCustomer.getStatus(),CustomerStatus.DISABLED);

        // Verify that the mock methods called
        verify(customerRepository,times(1)).findById(eq(customer.getId()));
        verify(customerRepository,times(1)).save(any());

    }

}
