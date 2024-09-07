package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Smtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmtpRepository extends JpaRepository<Smtp, Integer> {
    void deleteByEmail(String email);
    Optional<Smtp> findByEmailAndSmtp(String email, String smtp);
}