package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_inquiry")
@NoArgsConstructor
@Getter
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "inquiry", nullable = false)
    private String inquiry;

    @Column(name = "answer")
    private String answer;

    @Column(name = "inquiry_created_at", nullable = false)
    private LocalDateTime inquiryCreatedAt;

    @Column(name = "inquiry_updated_at")
    private LocalDateTime inquiryUpdatedAt;

    @Column(name = "answer_created_at")
    private LocalDateTime answerCreatedAt;

    @Column(name = "answer_updated_at")
    private LocalDateTime answerUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @Builder
    public Inquiry(User user, ParkingLot parkingLot, String inquiry) {
        this.user = user;
        this.parkingLot = parkingLot;
        this.inquiry = inquiry;
        this.inquiryCreatedAt = LocalDateTime.now();
        this.inquiryUpdatedAt = LocalDateTime.now();
    }

    // 문의 수정
    public void modifyInquiry(String inquiry) {
        this.inquiry = inquiry;
        this.inquiryUpdatedAt = LocalDateTime.now();
    }

    //답변 등록
    public void addAnswer(String answer) {
        this.answer = answer;
        this.answerCreatedAt = LocalDateTime.now();
        this.answerUpdatedAt = LocalDateTime.now();
    }

    //답변 수정
    public void modifyAnswer(String answer) {
        this.answer = answer;
        this.answerUpdatedAt = LocalDateTime.now();
    }

}
