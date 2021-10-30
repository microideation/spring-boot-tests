package com.microideation.tutorial.springtest.service;

import com.microideation.tutorial.springtest.dictionary.CustomerStatus;
import com.microideation.tutorial.springtest.domain.Customer;
import com.microideation.tutorial.springtest.repository.CustomerRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



    public Customer getCustomer(@NotNull Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No customer found with id: " + id));
    }

    public Customer registerCustomer(@NotNull Customer customer) {
        customer.setStatus(CustomerStatus.DISABLED);
        customer.validate();
        return customerRepository.save(customer);
    }

    public Customer activate(@NotNull Long custId) {
        Customer customer = getCustomer(custId);
        customer.activate();
        return customerRepository.save(customer);
    }
    
    public Customer deactivate(@NotNull Long custId) {
        Customer customer = getCustomer(custId);
        customer.deactivate();
        return customerRepository.save(customer);
    }
}
