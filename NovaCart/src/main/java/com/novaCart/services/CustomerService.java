package com.novaCart.services;

import com.novaCart.dto.CustomerDto;
import com.novaCart.dto.TopNCustomersOrders;
import com.novaCart.dto.TopOrdersResponse;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);

    List<TopNCustomersOrders> getTopNOrdersOfCustomers(Integer count);
}
