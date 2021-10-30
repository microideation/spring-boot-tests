package com.microideation.tutorial.springtest.customers;

import com.microideation.tutorial.springtest.domain.Customer;
import com.microideation.tutorial.springtest.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.Assert;

import static com.microideation.tutorial.springtest.customers.CustomerFixture.standardCustomer;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void givenFindByFirstnameAndLastname_whenFirstnameAndLastnameMatch_thenFetchCustomer() {
        // Create a customer
        Customer customer = standardCustomer();
        customer.setId(null);
        customer = customerRepository.save(customer);

        // Find
        Customer searchResult = customerRepository.findByFirstnameAndLastname(customer.getFirstname(),customer.getLastname());

        // Assertions
        Assertions.assertNotNull(searchResult);
        Assertions.assertEquals(searchResult.getId(),customer.getId());

    }

}
