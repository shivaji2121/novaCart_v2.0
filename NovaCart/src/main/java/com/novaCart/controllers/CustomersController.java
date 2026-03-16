package com.novaCart.controllers;

import com.novaCart.dto.CustomerDto;
import com.novaCart.dto.TopNCustomersOrders;
import com.novaCart.dto.TopOrdersResponse;
import com.novaCart.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
@Slf4j
public class CustomersController {

    private  final CustomerService customerService;


    @PostMapping(path = "/save")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto){
        CustomerDto savedCustomer=customerService.createCustomer(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/top-orders")
    public ResponseEntity<List<TopNCustomersOrders>> getTopNOrdersOfCustomers(@RequestParam(defaultValue = "3") Integer count) {
        return ResponseEntity.ok(customerService.getTopNOrdersOfCustomers(count));
    }
}
