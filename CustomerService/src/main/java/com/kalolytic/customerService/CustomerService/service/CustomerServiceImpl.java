package com.kalolytic.customerService.CustomerService.service;

import com.kalolytic.customerService.CustomerService.DTO.CustomerDTO;
import com.kalolytic.customerService.CustomerService.Mapper.CustMapper;
import com.kalolytic.customerService.CustomerService.model.Customer;
import com.kalolytic.customerService.CustomerService.model.ResourceNotFoundException;
import com.kalolytic.customerService.CustomerService.repo.CustomerRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustMapper custMapper;


    @Override
    @CacheEvict(value = "customer", allEntries = true)
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = custMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepo.save(newCustomer);
        return custMapper.toDTO(savedCustomer);
    }

    @Override
    @Cacheable(value = "customer", key = "#id")
    public CustomerDTO getCustomer(UUID id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for ID: " + id));
        return custMapper.toDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream().map(custMapper::toDTO).toList();
    }

    @Override
    @CacheEvict(value = "customer", key = "#id")
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        Customer existing = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for ID: " + id));

        existing.setFirstName(customerDTO.getFirstName());
        existing.setLastName(customerDTO.getLastName());
        existing.setEmail(customerDTO.getEmail());
        existing.setPassword(customerDTO.getPassword());
        existing.setPhoneNumber(customerDTO.getPhoneNumber());

        Customer saved = customerRepo.save(existing);
        return custMapper.toDTO(saved);
    }

    @Override
    public CustomerDTO verify(CustomerDTO dto) {
        return null;
    }

    @Override
    @CacheEvict(value = "customer", key = "#id")
    public String deleteCustomer(UUID id) {
        if (!customerRepo.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found for ID: " + id);
        }
        customerRepo.deleteById(id);
        return "Customer deleted successfully (ID: " + id + ")";
    }

     @Override
    public boolean existCustomer(UUID id) {
        return customerRepo.existsById(id);
    }
}
