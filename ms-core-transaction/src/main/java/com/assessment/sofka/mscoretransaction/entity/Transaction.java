package com.assessment.sofka.mscoretransaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movimientos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "movement_type")
    private String movementType;

    @Column(name = "status_record")
    private String statusRecord;

    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "removed_at")
    private Date removedAt;

    @Column(name = "available_balance", nullable = false)
    private BigDecimal availableBalance;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

}
