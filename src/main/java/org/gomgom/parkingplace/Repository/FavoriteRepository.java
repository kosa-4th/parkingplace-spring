package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Dto.FavoriteDto;
import org.gomgom.parkingplace.Entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 사용자 id로 즐겨찾기한 주차장 조회
     * @param userId 사용자 id
     * @return 주차장 id, 주차장 이름, 주차장 주소 dto 객체로 반환
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * 2024.09.10 양건모 | 정렬 순서 변경
     * */
    @Query("SELECT new org.gomgom.parkingplace.Dto.FavoriteDto$FavoriteParkingLotDto(" +
                "p.id, p.name, p.address" +
            ") " +
            "FROM Favorite f " +
            "JOIN f.parkingLot p " +
            "JOIN f.user u " +
            "where u.id = :userId " +
            "ORDER BY f.createdAt DESC")

    Page<FavoriteDto.FavoriteParkingLotDto> findFavoritesById(Pageable pageable, long userId);


}
