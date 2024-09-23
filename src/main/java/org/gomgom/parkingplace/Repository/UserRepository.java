package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.OpLE;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u, pn, ct " +
            "FROM User u " +
            "JOIN PlateNumber pn ON u.id = pn.user.id " +
            "JOIN pn.carType ct " +
            "WHERE u.id = :userId")
    List<Object[]> findUserWithCarType(@Param("userId") Long userId);

    @Query("SELECT u, pl FROM User u " +
            "JOIN ParkingLot pl " +
            "WHERE u.id = :userId")
    Optional<Object[]> findUserWithParkingLot(@Param("userId") Long userId);

    Page<User> findAllByAuth(Role auth, Pageable pageable);
    /**
     * 작성자: 오지수
     * 2024.09.02 : 회원가입, 로그인, 회원 유효성 여부 등
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

}
