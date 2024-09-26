package com.assessment.sofka.mscorepersona.repository;

import com.assessment.sofka.mscorepersona.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByIdentification(String identification);

    Optional<Customer> findByIdentificationAndStatusRecord(String identification, String statusRecord);

    List<Customer> findAllByStatusRecord(String statusRecord);
}
