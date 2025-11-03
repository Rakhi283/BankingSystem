package com.kalolytic.customerService.CustomerService.service;


import com.kalolytic.commonModel.CommonModel.DTO.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO dto);

    CustomerDTO getCustomer(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO deleteCustomer(UUID id);

    boolean existCustomer(UUID id);

    CustomerDTO updateCustomer(UUID id, CustomerDTO dto);

    CustomerDTO verify(CustomerDTO dto);
}