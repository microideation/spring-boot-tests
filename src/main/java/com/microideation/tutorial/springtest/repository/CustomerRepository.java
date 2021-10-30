package com.microideation.tutorial.springtest.repository;

import com.microideation.tutorial.springtest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByFirstnameAndLastname(String firstname,String lastName);
}
