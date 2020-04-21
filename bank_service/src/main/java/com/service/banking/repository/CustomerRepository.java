package com.service.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.banking.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
