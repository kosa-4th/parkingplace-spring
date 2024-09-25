package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.gomgom.parkingplace.enums.Bool;
import org.gomgom.parkingplace.enums.Role;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tbl_user")
@DynamicInsert // default값이 있다면 사용
@EntityListeners(AuditingEntityListener.class) // createdDate, LastModifiedDate 있다면 사용
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "google_id")
    private String googleId;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    @Column(name = "auth", nullable = false, length = 10)
    private Role auth;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'Y'")
    @Column(name = "usable", nullable = false)
    private Bool usable;

    @Builder
    public User(String name, String email, String password, String googleId, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.googleId = googleId;
        this.auth = role == null ? Role.ROLE_USER : role;
    }

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void deleteUser() {
        this.usable = Bool.N;
    }

    // 회원 정보 수정
    public void update(String newPassword) {
        this.password = password;
    }

}