package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_parking_image")
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ParkingImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_image_id", nullable = false)
    private Long id;

    @Size(max = 300)
    @NotNull
    @Column(name = "image_path", nullable = false, length = 300)
    private String imagePath;

    @Size(max = 300)
    @Column(name = "thumbnail_path", length = 300)
    private String thumbnailPath;

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
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    public ParkingImage(String imagePath, String thumbnailPath, ParkingLot parkingLot) {
        this.imagePath = imagePath;
        this.thumbnailPath = thumbnailPath;
        this.parkingLot = parkingLot;
    }
}
