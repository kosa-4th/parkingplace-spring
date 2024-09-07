package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "refresh_token", nullable = false, length = 1000)
    private String refreshToken;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public RefreshToken(User user, String refreshToken) {
        this.refreshToken = refreshToken;
        this.user = user;
    }

}
