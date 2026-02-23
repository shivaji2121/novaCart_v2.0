package com.novaCart.services.Impl;

import com.novaCart.dto.AddressDto;
import com.novaCart.dto.CustomerDto;
import com.novaCart.entity.AddressEntity;
import com.novaCart.entity.CustomersEntity;
import com.novaCart.repository.CustomerRepository;
import com.novaCart.services.CustomerService;
import com.novaCart.utils.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if(customerDto.getCustomerStatus()==null){
            customerDto.setCustomerStatus(CustomerStatus.ACTIVE);
        }
        CustomersEntity customersEntity=CustomersEntity.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .customerStatus(customerDto.getCustomerStatus())
                .age(customerDto.getAge())
                .dateOfBirth(customerDto.getDateOfBirth())
                .build();

        if(customerDto.getAddresses()!=null && !customerDto.getAddresses().isEmpty()){
            List<AddressEntity> addressEntityList=customerDto.getAddresses().stream()
                    .map(addressDto -> AddressEntity.builder()
                            .city(addressDto.getCity())
                            .country(addressDto.getCountry())
                            .state(addressDto.getState())
                            .zipCode(addressDto.getZipCode())
                            .customer(customersEntity)
                            .build())
                    .toList();

            customersEntity.setAddress(addressEntityList);
        }

        CustomersEntity savedCustomer = customerRepository.save(customersEntity);


        return toDto(savedCustomer);


    }

    private CustomerDto toDto(CustomersEntity customer) {
        List<AddressDto> addressDtos = customer.getAddress().stream()
                .map(a -> AddressDto.builder()
                        .id(a.getId())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .state(a.getState())
                        .country(a.getCountry())
                        .zipCode(a.getZipCode())
                        .createdAt(a.getCreatedAt())
                        .updatedAt(a.getUpdatedAt())
                        .deletedAt(a.getDeletedAt())
                        .build())
                .toList();

        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .customerStatus(customer.getCustomerStatus())
                .age(customer.getAge())
                .dateOfBirth(customer.getDateOfBirth())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .deletedAt(customer.getDeletedAt())
                .addresses(addressDtos)
                .build();

    }
}
