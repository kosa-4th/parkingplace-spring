package org.gomgom.parkingplace.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "tbl_smtp")
@Entity
public class Smtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smtp_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "smtp", nullable = false)
    private String smtp;

    @Builder
    public Smtp(String email, String smtp) {
        this.email = email;
        this.smtp = smtp;
    }

}
