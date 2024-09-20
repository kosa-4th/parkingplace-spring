package org.gomgom.parkingplace.Entity;

import jakarta.annotation.Resource;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_parking_lot")
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "parking_center_id")
    private String parkingCenterId;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 300)
    @NotNull
    @Column(name = "address", nullable = false, length = 300)
    private String address;

    @Column(name = "tel")
    private String tel;

    @Column(name = "parking_type")
    private String parkingType;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'")
    @Column(name = "wash", nullable = false)
    private Bool wash;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'")
    @Column(name = "maintenance", nullable = false)
    private Bool maintenance;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "weekdays_open_time")
    private LocalTime weekdaysOpenTime;

    @Column(name = "weekdays_close_time")
    private LocalTime weekdaysCloseTime;

    @Column(name = "weekend_open_time")
    private LocalTime weekendOpenTime;

    @Column(name = "weekend_close_time")
    private LocalTime weekendCloseTime;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'")
    @Column(name = "usable", nullable = false)
    private Bool usable;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //ManyToOne에서 OneToOne으로 변경
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private List<ParkingImage> parkingImages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private List<ParkingSpace> parkingSpaces = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private List<Review> reviews;

    @Builder
    public ParkingLot(String name, String address, String tel,String parkingType, String parkingCenterId,
                      double longitude, double latitude,
                      LocalTime weekdaysOpenTime, LocalTime weekdaysCloseTime,
                      LocalTime weekendOpenTime, LocalTime weekendCloseTime) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.parkingType = parkingType;
        this.parkingCenterId = parkingCenterId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.weekdaysOpenTime = weekdaysOpenTime;
        this.weekdaysCloseTime = weekdaysCloseTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;

    }

}