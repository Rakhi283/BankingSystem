package com.kalolytic.customerService.CustomerService.Mapper;


import com.kalolytic.commonModel.CommonModel.DTO.CustomerDTO;
import com.kalolytic.customerService.CustomerService.model.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustMapper {

  public Customer toEntity(CustomerDTO dto) {
    Customer entity = new Customer();
    entity.setCustId(dto.getCustid());
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setEmail(dto.getEmail());
    entity.setPassword(dto.getPassword());
    entity.setPhoneNumber(dto.getPhoneNumber());
    return entity;
  }

  public CustomerDTO toDTO(Customer entity) {
      CustomerDTO dto = new CustomerDTO();
      dto.setCustid(entity.getCustId());
      dto.setFirstName(entity.getFirstName());
      dto.setLastName(entity.getLastName());
      dto.setEmail(entity.getEmail());
      dto.setPassword(entity.getPassword());
      dto.setPhoneNumber(entity.getPhoneNumber());

      return dto;
  }
}
