package com.kalolytic.customerService.CustomerService.service;

import com.kalolytic.commonModel.CommonModel.DTO.CustomerDTO;
import com.kalolytic.commonModel.CommonModel.exception.DuplicateElementFoundException;
import com.kalolytic.commonModel.CommonModel.exception.InvalidInputException;
import com.kalolytic.customerService.CustomerService.Mapper.CustMapper;
import com.kalolytic.customerService.CustomerService.model.Customer;
import com.kalolytic.commonModel.CommonModel.exception.ResourceNotFoundException;
import com.kalolytic.customerService.CustomerService.repo.CustomerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustMapper custMapper;

    @Override
    @CacheEvict(value = "customer", allEntries = true)
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        log.info("Creating new customer: {}", customerDTO);

        try {
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (customerDTO.getEmail() == null || !Pattern.matches(emailRegex, customerDTO.getEmail())) {
                log.error("Invalid email format received: {}", customerDTO.getEmail());
                throw new InvalidInputException("Invalid email format!");
            }

            if (customerDTO.getPhoneNumber() == null) {
                log.error("Phone number is missing.");
                throw new InvalidInputException("Phone number cannot be null or empty.");
            }

            if (customerDTO.getPhoneNumber().toString().length() != 10) {
                log.error("Invalid phone number length: {}", customerDTO.getPhoneNumber());
                throw new InvalidInputException("Phone number should be of 10 digits.");
            }

            Customer newCustomer = custMapper.toEntity(customerDTO);
            Customer savedCustomer = customerRepo.save(newCustomer);
            log.info("Customer created successfully with ID: {}", savedCustomer.getCustId());
            return custMapper.toDTO(savedCustomer);

        } catch (InvalidInputException e) {
            throw e; // Do not log here again, already logged above
        } catch (Exception e) {
            log.error("Unexpected error while creating customer: {}", e.getMessage(), e);
            throw new DuplicateElementFoundException("Already Email exists. Please try again with unique email id ");
        }
    }

    @Override
    @Cacheable(value = "customer", key = "#id")
    public CustomerDTO getCustomer(UUID id) {
        log.info("Fetching customer by ID: {}", id);

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found for ID: {}", id);
                    return new ResourceNotFoundException("Customer not found for ID: " + id);
                });

        log.info("Customer fetched successfully: {}", customer.getCustId());
        return custMapper.toDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Fetching all customers...");
        List<Customer> customers = customerRepo.findAll();
        log.info("Total customers found: {}", customers.size());
        return customers.stream().map(custMapper::toDTO).toList();
    }

    @Override
    @CacheEvict(value = "customer", key = "#id")
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        log.info("Updating customer with ID: {}", id);

        Customer existing = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found for ID: {}", id);
                    return new ResourceNotFoundException("Customer not found for ID: " + id);
                });

        log.info("Customer found with ID: {}", existing.getCustId());

        if (customerDTO.getFirstName() != null && !customerDTO.getFirstName().isBlank())
            existing.setFirstName(customerDTO.getFirstName());

        if (customerDTO.getLastName() != null && !customerDTO.getLastName().isBlank())
            existing.setLastName(customerDTO.getLastName());

        if (customerDTO.getEmail() != null && !customerDTO.getEmail().isBlank())
            existing.setEmail(customerDTO.getEmail());

        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isBlank())
            existing.setPassword(customerDTO.getPassword());

        if (customerDTO.getPhoneNumber() != null)
            existing.setPhoneNumber(customerDTO.getPhoneNumber());

        Customer saved = customerRepo.save(existing);
        log.info("Customer updated successfully: {}", saved.getCustId());
        return custMapper.toDTO(saved);
    }


    @Override
    public CustomerDTO verify(CustomerDTO dto) {
        log.warn("verify() method is not implemented yet.");
        return null;
    }

    @Override
    @CacheEvict(value = "customer", key = "#id")
    public CustomerDTO deleteCustomer(UUID id) {
        log.info("Deleting customer with ID: {}", id);

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found for ID: {}", id);
                    return new ResourceNotFoundException("Customer not found for ID: " + id);
                });

        CustomerDTO dto = custMapper.toDTO(customer);
        customerRepo.deleteById(id);

        log.info("Customer deleted successfully: {}", id);
        return dto;
    }

    @Override
    public boolean existCustomer(UUID id) {
        log.info("Checking if customer exists with ID: {}", id);
        boolean exists = customerRepo.existsById(id);
        log.info("Customer exists: {}", exists);
        return exists;
    }
}
