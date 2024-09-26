package com.assessment.sofka.mscoretransaction.repository;

import com.assessment.sofka.mscoretransaction.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Integer> {


    boolean existsByAccountNumberAndStatusRecord(String accountNumber, String statusRecord);

    Optional<Account> findByAccountNumberAndStatusRecord(String accountNumber, String statusRecord);

    List<Account> findByCustomerIdAndStatusRecord(Integer customerId, String statusRecord);
}
