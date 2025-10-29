package com.kalolytic.customerService.CustomerService.controller;

import com.kalolytic.customerService.CustomerService.DTO.CustomerDTO;
import com.kalolytic.customerService.CustomerService.controller.ResponseStructure;
import com.kalolytic.customerService.CustomerService.model.Customer;
import com.kalolytic.customerService.CustomerService.repo.CustomerRepo;
import com.kalolytic.customerService.CustomerService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(@RequestBody CustomerDTO dto){
        CustomerDTO user = customerService.verify(dto);
        ResponseStructure<String> response = new ResponseStructure<>("user is logged in successfully",HttpStatus.OK.toString(),user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseStructure<CustomerDTO>> createCustomer(@RequestBody CustomerDTO dto) {
        CustomerDTO saved = customerService.createCustomer(dto);
        ResponseStructure<CustomerDTO> response = new ResponseStructure<>(
                "Customer created successfully!", HttpStatus.CREATED.toString(), saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<CustomerDTO>> getCustomer(@PathVariable UUID id) {
        CustomerDTO dto = customerService.getCustomer(id);
        ResponseStructure<CustomerDTO> response = new ResponseStructure<>(
                "Customer fetched successfully!", HttpStatus.OK.toString(), dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseStructure<List<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> list = customerService.getAllCustomers();
        ResponseStructure<List<CustomerDTO>> response = new ResponseStructure<>(
                "Customers fetched successfully!", HttpStatus.OK.toString(), list);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<CustomerDTO>> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO dto) {
        CustomerDTO updated = customerService.updateCustomer(id, dto);
        ResponseStructure<CustomerDTO> response = new ResponseStructure<>(
                "Customer updated successfully!", HttpStatus.OK.toString(), updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteCustomer(@PathVariable UUID id) {
        String message = customerService.deleteCustomer(id);
        ResponseStructure<String> response = new ResponseStructure<>(
                message, HttpStatus.OK.toString(), message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<ResponseStructure<Boolean>> existCustomer(@PathVariable UUID id) {
        boolean exists = customerService.existCustomer(id);
        String message = exists ? "Customer exists!" : "Customer not found!";
        HttpStatus status = exists ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseStructure<Boolean> response = new ResponseStructure<>(message, status.toString(), exists);
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/auth/validate")
    public ResponseEntity<CustomerDTO> validateUser(
            @RequestParam String email,
            @RequestParam String password) {

        Customer customer = customerRepo.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        CustomerDTO dto = new CustomerDTO(
                customer.getCustId(),
                customer.getFirstName(),
                customer.getEmail()
        );

        return ResponseEntity.ok(dto);
    }

}
