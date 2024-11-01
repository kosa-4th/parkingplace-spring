package org.gomgom.parkingplace.Entity;

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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_plate_number")
@DynamicInsert
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plate_number_id", nullable = false)
    private Long id;

    @Size(max = 8)
    @NotNull
    @Column(name = "plate_number", nullable = false, length = 8)
    private String plateNumber;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'")
    @Column(name = "usable", nullable = false)
    private Bool usable;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_type_id", nullable = false)
    private CarType carType;

    @Builder
    public PlateNumber(User user, CarType carType, String plateNumber) {
        this.user = user;
        this.carType = carType;
        this.plateNumber = plateNumber;
    }
}
