package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tbl_plate_number")
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plate_num_id", nullable = false)
    private Long id;

    @Size(max = 8)
    @NotNull
    @Column(name = "plate_number", nullable = false, length = 8)
    private String plateNumber;

    @NotNull
    @Column(name = "usable", nullable = false)
    private Character usable;

    @NotNull
    @CreatedDate
    @Column(name = "create_at", nullable = false)
    private Instant createAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_type_id", nullable = false)
    private CarType carType;
}
