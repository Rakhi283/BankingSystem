package com.kalolytic.customerService.CustomerService.repo;

import com.kalolytic.customerService.CustomerService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmailAndPassword(String email, String password);
}
