package com.microideation.tutorial.springtest.controller;

import com.microideation.tutorial.springtest.domain.Customer;
import com.microideation.tutorial.springtest.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.registerCustomer(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCustomer);
    }

    @GetMapping("/{custId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long custId) {
        Customer customer = customerService.getCustomer(custId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customer);
    }

    @PutMapping("/activate")
    public ResponseEntity<Customer> activateCustomer(@RequestParam Long custId) {
        Customer savedCustomer = customerService.activate(custId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savedCustomer);
    }

    @PutMapping("/deactivate")
    public ResponseEntity<Customer> deactivateCustomer(@RequestParam Long custId) {
        Customer savedCustomer = customerService.deactivate(custId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savedCustomer);
    }
}
