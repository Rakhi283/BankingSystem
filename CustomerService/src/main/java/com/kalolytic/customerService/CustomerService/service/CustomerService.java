package com.kalolytic.customerService.CustomerService.service;


import com.kalolytic.customerService.CustomerService.DTO.CustomerDTO;
import com.kalolytic.customerService.CustomerService.controller.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO dto);

    CustomerDTO getCustomer(UUID id);

    List<CustomerDTO> getAllCustomers();

    String deleteCustomer(UUID id);

    boolean existCustomer(UUID id);

    CustomerDTO updateCustomer(UUID id, CustomerDTO dto);

    CustomerDTO verify(CustomerDTO dto);
}