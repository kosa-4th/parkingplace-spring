package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 작성자: 오지수
    // 회원가입, 로그인
    Optional<User> findByEmail(String email);

}
