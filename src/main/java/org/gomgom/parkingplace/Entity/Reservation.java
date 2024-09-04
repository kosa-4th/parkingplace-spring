package org.gomgom.parkingplace.Entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gomgom.parkingplace.enums.Bool;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @Author : 김경민
 * @*/
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_reservation")
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long id;

    @Column(name ="reservation_uuid", nullable = false)
    private String reservationUuid;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @NotNull
    @Column(name = "wash", nullable = false)
    private Character wash;

    @NotNull
    @Column(name = "maintenance", nullable = false)
    private Character maintenance;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @NotNull
    @ColumnDefault("'N'")
    @Column(name = "reservation_confirmed", nullable = false)
    private Bool reservationConfirmed;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plate_num_id", nullable = false)
    private PlateNumber plateNum;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // 필수 필드를 포함한 Builder 생성
    @Builder
    public Reservation(Instant startTime, Instant endTime, Character wash, Character maintenance, Integer totalPrice,
                       Bool reservationConfirmed, PlateNumber plateNum, ParkingLot parkingLot, String reservationUuid, User user) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.wash = wash;
        this.maintenance = maintenance;
        this.totalPrice = totalPrice;
        this.reservationConfirmed = reservationConfirmed;
        this.plateNum = plateNum;
        this.parkingLot = parkingLot;
        this.reservationUuid = reservationUuid;
        this.user = user;
    }

}
