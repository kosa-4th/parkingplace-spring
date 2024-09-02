package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tbl_parking_space")
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_space_id", nullable = false)
    private Long id;

    @Column(name = "weekdays_price")
    private Integer weekdaysPrice;

    @Column(name = "weekend_price")
    private Integer weekendPrice;

    @Column(name = "wash_price")
    private Integer washPrice;

    @Column(name = "maintenance_price")
    private Integer maintenancePrice;

    @NotNull
    @Column(name = "available_space_num", nullable = false)
    private Integer availableSpaceNum;

    @Column(name = "next_space_num")
    private Integer nextSpaceNum;

    @Column(name = "next_space_date")
    private Instant nextSpaceDate;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_type_id", nullable = false)
    private CarType carType;
}
