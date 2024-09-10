package org.gomgom.parkingplace.favorite;

import jakarta.validation.constraints.AssertTrue;
import org.gomgom.parkingplace.Dto.FavoriteDto;
import org.gomgom.parkingplace.Service.favorite.FavoriteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.NoSuchElementException;

@SpringBootTest
@Rollback
public class FavoriteTest {
    @Autowired
    private FavoriteService favoriteService;

    @Test
    @DisplayName("즐겨찾기 토글 정상 동작 테스트")
    public void favoriteToggleTest() {
        FavoriteDto.FavoriteToggleResponseDto dto1 = favoriteService.toggleFavorite(1, 4);
        FavoriteDto.FavoriteToggleResponseDto dto2 = favoriteService.toggleFavorite(1, 4);
        System.out.println(dto1.getToggleResult());
        System.out.println(dto2.getToggleResult());

        Assertions.assertTrue(dto1.getToggleResult() != dto2.getToggleResult());
    }

    @Test
    @DisplayName("없는 사용자 에러 발생 테스트")
    public void noUserExistTest() {
        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> {
            FavoriteDto.FavoriteToggleResponseDto dto1 = favoriteService.toggleFavorite(Long.MAX_VALUE, 4);
        });
    }

    @Test
    @DisplayName("없는 주차장 id 에러 발생 테스트")
    public void noParkingLotExistTest() {
        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> {
            FavoriteDto.FavoriteToggleResponseDto dto1 = favoriteService.toggleFavorite(1, Long.MAX_VALUE);
        });
    }

    @Test
    @DisplayName("없는 사용자, 주자창 에러 발생 테스트")
    public void noUserAndParkingLotExistTest() {
        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> {
            FavoriteDto.FavoriteToggleResponseDto dto1 = favoriteService.toggleFavorite(Long.MAX_VALUE, Long.MAX_VALUE);
        });
    }

    @Test
    @DisplayName("즐겨찾기 한 주차장 목록 조회 정상 동작 테스트")
    public void findFavoritesTest() {
        for (int i = 1; i < 10; i++) {
            FavoriteDto.FavoriteToggleResponseDto dto1 = favoriteService.toggleFavorite(1, i);
        }

        for (int i = 1; i < 10; i++) {
            FavoriteDto.FavoriteToggleResponseDto dto1 = favoriteService.toggleFavorite(3, i);
        }

        System.out.println(favoriteService.myFavorites(3).getFavorites().size());
        Assertions.assertTrue(favoriteService.myFavorites(3).getFavorites().size() == 9);
    }
}
