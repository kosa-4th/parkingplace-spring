package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gomgom.parkingplace.enums.Bool;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_review")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "review", nullable = false)
    private String review;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Double rating;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'")
    @Column(name = "complaint")
    private Bool complaint;

    @Column(name = "complaint_reason")
    private String complaintReason;

    @Column(name = "complaint_date")
    private LocalDateTime complaintDate;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @Builder
    public Review(User user, ParkingLot parkingLot, String review, Double rating) {
        this.user = user;
        this.parkingLot = parkingLot;
        this.review = review;
        this.rating = rating;
        this.complaint = Bool.N;
    }

    public void complainReview(String reason) {
        this.complaintReason = reason;
        this.complaint = Bool.C;
        this.complaintDate = LocalDateTime.now();
    }

    public void modifyComplaint(Bool complaint) {
        this.complaint = complaint;
    }
}