package com.assessment.sofka.mscoretransaction.repository;

import com.assessment.sofka.mscoretransaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {


    /**
     * Method to find transactions by account id and between dates  of creation of the transaction
     *
     * @param createdAt  date of creation of the transaction
     * @param createdAt2 date of creation of the transaction
     * @param idAccount  account id
     * @return
     */
    @Query("SELECT t FROM Transaction t WHERE DATE(t.createdAt) BETWEEN ?1 AND ?2 AND t.account.accountId = ?3" +
            " ORDER BY t.createdAt DESC")
    List<Transaction> findByCreatedAtBetween(Date createdAt, Date createdAt2,
                                             Integer idAccount);
}
