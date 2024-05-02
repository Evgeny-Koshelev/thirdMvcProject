package org.example.services;

import org.example.entities.Customers;
import org.example.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customers getById(UUID id) {
        Optional<Customers> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElseGet(Customers::new);
    }

    public List<Customers> getAll() {
        return (List<Customers>) customerRepository.findAll();
    }

    @Transactional
    public Customers save(Customers customers) {
        return customerRepository.save(customers);
    }

    @Transactional
    public Customers delete(UUID id) {
        Customers customers = getById(id);
        if(customers.getId() != null)
            customerRepository.deleteById(id);
        return customers;
    }
}
