package com.kalolytic.customerService.CustomerService.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID custid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private BigInteger phoneNumber;

    public CustomerDTO(UUID custId, String firstName, String email) {
        this.custid = custId;
        this.firstName = firstName;
        this.email = email;
    }
}
