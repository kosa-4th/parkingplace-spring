package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_parking_space")
@ToString(exclude = {"parkingLot"})  // 순환 참조를 방지하기 위해 제외
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class ParkingSpace extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_space_id", nullable = false)
    private Long id;

    @Column(name = "space_name")
    private String spaceName;

    @Column(name = "weekdays_price")
    private Integer weekdaysPrice;

    @Column(name = "weekend_price")
    private Integer weekendPrice;

    @Column(name="week_all_day_price")
    private Integer weekAllDayPrice;

    @Column(name="weekend_all_day_price")
    private Integer weekendAllDayPrice;

    @Column(name = "wash_price")
    private Integer washPrice;

    @Column(name = "maintenance_price")
    private Integer maintenancePrice;

    @NotNull
    @Column(name = "available_space_num", nullable = false)
    private Integer availableSpaceNum;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_type_id", nullable = false)
    private CarType carType;

    @Builder
    public ParkingSpace(ParkingLot parkingLot, String spaceName, CarType carType, int availableSpaceNum,
                        int weekdaysPrice, int weekAllDayPrice,
                        int weekendPrice, int weekendAllDayPrice,
                        int washPrice, int maintenancePrice) {
        this.parkingLot = parkingLot;
        this.spaceName = spaceName;
        this.carType = carType;
        this.availableSpaceNum = availableSpaceNum;
        this.weekdaysPrice = weekdaysPrice;
        this.weekAllDayPrice = weekAllDayPrice;
        this.weekendPrice = weekendPrice;
        this.weekendAllDayPrice = weekendAllDayPrice;
        this.washPrice = washPrice;
        this.maintenancePrice = maintenancePrice;
    }

    public void setPlusAvailableSpaceNum(int availableSpaceNum) {
        this.availableSpaceNum += availableSpaceNum;
    }
}
