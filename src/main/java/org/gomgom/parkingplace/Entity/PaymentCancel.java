package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @Date 2024.09.13 결제 취소 Entity 수정
 * */
@Getter
@Setter
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name = "tbl_payment_cancel")
@EntityListeners(AuditingEntityListener.class)
public class PaymentCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancle_id", nullable = false)
    private Long id;

    @Column(name = "pg_tid")
    private String pgTid;

    @Column(name = "amount")
    private double amount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "receipt_url")
    private String receiptUrl;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_email")
    private String buyerEmail;

    @Column(name = "merchant_uid")
    private String merchant_uid;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime creatAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;


}
