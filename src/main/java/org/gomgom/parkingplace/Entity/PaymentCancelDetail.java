package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_cancel_detail")
public class PaymentCancelDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @Column(name = "pg_tid")
    private String pgTid;

    @Column(name = "amount")
    private double amount;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "reason")
    private String reason;

    @Column(name = "receipt_url")
    private String receiptUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

}
