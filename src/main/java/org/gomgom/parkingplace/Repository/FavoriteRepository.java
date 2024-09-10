package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 사용자 id, 주차장 id로 즐겨찾기 조회
     * @param userId 사용자 id
     * @param parkingLotId 주차장 id
     * @return favorite 객체 Optional 형태로 반환
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * */
    @Query("SELECT f FROM Favorite f " +
            "where f.user.id = :userId " +
            "AND f.parkingLot.id = :parkingLotId")
    Optional<Favorite> findFavorite(long userId, long parkingLotId);
}
