package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 300)
    @NotNull
    @Column(name = "address", nullable = false, length = 300)
    private String address;

    @NotNull
    @Column(name = "wash", nullable = false)
    private Character wash;

    @NotNull
    @Column(name = "maintenance", nullable = false)
    private Character maintenance;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longtitude")
    private Integer longtitude;

    @Column(name = "weekdays_open_time")
    private LocalTime weekdaysOpenTime;

    @Column(name = "weekdays_close_time")
    private LocalTime weekdaysCloseTime;

    @Column(name = "weekend_open_time")
    private LocalTime weekendOpenTime;

    @Column(name = "weekend_close_time")
    private LocalTime weekendCloseTime;

    @NotNull
    @Column(name = "usable", nullable = false)
    private Character usable;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}