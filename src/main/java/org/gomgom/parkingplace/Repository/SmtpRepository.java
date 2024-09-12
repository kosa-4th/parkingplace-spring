package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Smtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmtpRepository extends JpaRepository<Smtp, Integer> {
    /**
     * 작성자: 오지수
     * 2024.09.07 : 인증번호 지우기
     * @param email
     */
    void deleteByEmail(String email);

    /**
     * 작성자: 오지수
     * 2024.09.07 : 이메일과 인증번호가 일치하는지 확인
     * @param email
     * @param smtp
     * @return
     */
    Optional<Smtp> findByEmailAndSmtp(String email, String smtp);
}