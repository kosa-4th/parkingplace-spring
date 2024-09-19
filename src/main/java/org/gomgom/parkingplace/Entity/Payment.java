package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.gomgom.parkingplace.enums.Bool;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

/**
 *
 * @Date 2024.09.14
 * Entity수정 (Confirmed추가)
 * */
@Getter
@Setter
@Entity
@Table(name = "TBL_PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "imp_uid", length = 255)
    private String impUid;

    @Column(name = "pg_tid", length = 255)
    private String pgTid;

    @Column(name = "merchant_uid", length = 255)
    private String merchantUid;
    @Column(name = "payment_method", length = 255)
    private String paymentMethod;

    @Column(name = "card_name", length = 255)
    private String cardName;

    @Column(name = "card_number", length = 255)
    private String cardNumber;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "buyer_name", length = 255)
    private String buyerName;

    @Column(name = "buyer_email", length = 255)
    private String buyerEmail;

    @Column(name = "buyer_tel", length = 255)
    private String buyerTel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'Y'") //취소시 -> Y->D
    @Column(name = "payment_confirmed", nullable = false)
    private Bool paymentConfirmed;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "paid_at")
    private String paidAt;

    @Column(name = "failed_at")
    private String failedAt;

    @Column(name = "receipt_url", length = 255)
    private String receiptUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_ID", nullable = false)
    private Reservation reservation;

}