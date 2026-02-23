package com.novaCart.services.Impl;

import com.novaCart.dto.CustomerDto;
import com.novaCart.entity.CustomersEntity;
import com.novaCart.repository.CustomerRepository;
import com.novaCart.services.CustomerService;
import com.novaCart.utils.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private  final ModelMapper modelMapper;



    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        customerRepository.findActiveCustomerByEmail(customerDto.getEmail())
                .ifPresent(existing -> {
                    throw new RuntimeException("Email already exists");
                });
        CustomersEntity customersEntity=modelMapper.map(customerDto,CustomersEntity.class);
        if(customersEntity.getCustomerStatus()==null){
            customersEntity.setCustomerStatus(CustomerStatus.ACTIVE);
        }
        CustomersEntity savedUser=customerRepository.save(customersEntity);
        return modelMapper.map(savedUser,CustomerDto.class);
    }
}
