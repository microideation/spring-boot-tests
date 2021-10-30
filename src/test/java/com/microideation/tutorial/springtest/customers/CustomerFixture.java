package com.microideation.tutorial.springtest.customers;

import com.microideation.tutorial.springtest.dictionary.CustomerStatus;
import com.microideation.tutorial.springtest.domain.Customer;

public class CustomerFixture {
    public static Customer standardCustomer() {
        return Customer.builder()
                .firstname("John")
                .lastname("Doe")
                .id(1l)
                .status(CustomerStatus.DISABLED)
                .build();
    }
}
