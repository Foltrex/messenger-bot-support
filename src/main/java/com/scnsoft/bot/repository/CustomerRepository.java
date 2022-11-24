package com.scnsoft.bot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scnsoft.bot.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
