package com.microideation.tutorial.springtest.customers;

import com.microideation.tutorial.springtest.dictionary.CustomerStatus;
import com.microideation.tutorial.springtest.domain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.microideation.tutorial.springtest.customers.CustomerFixture.standardCustomer;

public class CustomerDomainTest {

    @Test
    public void givenValidate_whenFirstNameEmpty_thenThrowsException() {
        Customer customer=standardCustomer();
        customer.setFirstname(null);
        Assertions.assertThrows(RuntimeException.class,() -> {
            customer.validate();
        });
    }

    @Test
    public void givenValidate_whenLastNameEmpty_thenThrowsException() {
        Customer customer=standardCustomer();
        customer.setLastname(null);
        Assertions.assertThrows(RuntimeException.class,() -> {
            customer.validate();
        });
    }

    @Test
    public void givenValidate_whenStatusEmpty_thenThrowsException() {
        Customer customer=standardCustomer();
        customer.setStatus(null);
        Assertions.assertThrows(RuntimeException.class,() -> {
            customer.validate();
        });
    }


    @Test
    public void givenActivate_whenTriggred_thenSetStatusToActive() {
        Customer customer=standardCustomer();
        customer.activate();

        // Validate that the status is active
        Assertions.assertEquals(CustomerStatus.ACTIVE,customer.getStatus());
    }


    @Test
    public void givenDeactivate_whenTriggred_thenSetStatusToDisabled() {
        Customer customer=standardCustomer();
        customer.deactivate();

        // Validate that the status is active
        Assertions.assertEquals(CustomerStatus.DISABLED,customer.getStatus());
    }


}
