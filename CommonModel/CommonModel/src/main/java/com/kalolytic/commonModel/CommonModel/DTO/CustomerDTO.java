package com.kalolytic.commonModel.CommonModel.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private UUID custid;
    @NotBlank
    private String firstName;
    private String lastName;
    @Email
    @NotBlank
    private String email;
    private String password;
    private BigInteger phoneNumber;

    public CustomerDTO(UUID custId, String firstName, String email) {
        this.custid = custId;
        this.firstName = firstName;
        this.email = email;
    }
}
