package com.kalolytic.customerService.CustomerService.controller;

import com.kalolytic.commonModel.CommonModel.DTO.CustomerDTO;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import com.kalolytic.customerService.CustomerService.model.Customer;
import com.kalolytic.customerService.CustomerService.repo.CustomerRepo;
import com.kalolytic.customerService.CustomerService.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(@RequestBody CustomerDTO dto) {
        log.info("Login request received for email: {}", dto.getEmail());
        CustomerDTO user = customerService.verify(dto);
        log.info("Login successful for email: {}", dto.getEmail());
        ResponseStructure<String> response = new ResponseStructure<>("user is logged in successfully", "SUCCESS", user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseStructure<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerDTO dto) {
        log.info("Create customer request received: {}", dto);
        CustomerDTO saved = customerService.createCustomer(dto);
        log.info("Customer created successfully with ID: {}", saved.getCustid());
        ResponseStructure<CustomerDTO> response = new ResponseStructure<>("Customer created successfully with ID:" + saved.getCustid(), "SUCCESS", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<CustomerDTO>> getCustomer(@PathVariable UUID id) {
        log.info("Fetch customer request for ID: {}", id);
        CustomerDTO dto = customerService.getCustomer(id);
        log.info("Customer fetched successfully for ID: {}", id);
        ResponseStructure<CustomerDTO> response = new ResponseStructure<>("Customer fetched successfully!", "SUCCESS", dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseStructure<List<CustomerDTO>>> getAllCustomers() {
        log.info("Fetching all customers");
        List<CustomerDTO> list = customerService.getAllCustomers();
        log.info("Total customers fetched: {}", list.size());
        ResponseStructure<List<CustomerDTO>> response = new ResponseStructure<>("All Customer fetched successfully!", "SUCCESS", list);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseStructure<CustomerDTO>> updateCustomer(@PathVariable UUID id, @Valid @RequestBody CustomerDTO dto) {
        log.info("Update request received for Customer ID: {}", id);
        CustomerDTO updated = customerService.updateCustomer(id, dto);
        log.info("Customer updated successfully for ID: {}", id);
        ResponseStructure<CustomerDTO> response = new ResponseStructure<>("Customer Updated Successfully", "SUCCESS", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseStructure<CustomerDTO>> deleteCustomer(@PathVariable UUID id) {
        log.info("Delete request received for Customer ID: {}", id);
        CustomerDTO customer = customerService.deleteCustomer(id);
        log.info("Customer deleted successfully with ID: {}", id);
        ResponseStructure<CustomerDTO> response = new ResponseStructure<>("customer deleted successfully!", "SUCCESS", customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<ResponseStructure<Boolean>> existCustomer(@PathVariable UUID id) {
        log.info("Check existence for Customer ID: {}", id);
        boolean exists = customerService.existCustomer(id);
        ResponseStructure<Boolean> response = new ResponseStructure<>();
        if (exists) {
            log.info("Customer exists with ID: {}", id);
            response.setData(true);
            response.setStatus("SUCCESS");
            response.setMessage("Customer exists!");
            return ResponseEntity.ok(
                    response
            );
        } else {
            log.warn("Customer not found with ID: {}", id);
            response.setData(false);
            response.setStatus("FAILURE");
            response.setMessage("Customer doesn't exists!");
            return ResponseEntity.ok(
                    response
            );
        }
    }
        @GetMapping("/auth/validate")
        public ResponseEntity<ResponseStructure<CustomerDTO>> validateUser (
                @RequestParam String email,
                @RequestParam String password){

            log.info("Validating login for email: {}", email);

            Customer customer = customerRepo.findByEmailAndPassword(email, password)
                    .orElseThrow(() -> {
                        log.error("Invalid login attempt for email: {}", email);
                        return new RuntimeException("Invalid email or password");
                    });

            CustomerDTO dto = new CustomerDTO(
                    customer.getCustId(),
                    customer.getFirstName(),
                    customer.getEmail(),
                    customer.getPassword(),
                    customer.getLastName(),
                    customer.getPhoneNumber()
            );

            log.info("Login validated successfully for email: {}", email);
            ResponseStructure<CustomerDTO> response = new ResponseStructure<>("Login successful!", "SUCCESS", customer);
            return ResponseEntity.ok(response);
        }


}
