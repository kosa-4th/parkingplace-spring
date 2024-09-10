package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FavoriteDto {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 즐겨찾기 토글 응답 DTO
     * ---------------------
     * 2024.09.05 양건모 | 기능 구현
     */
    @RequiredArgsConstructor
    @Getter
    public static class FavoriteToggleResponseDto {
        private final long parkingLotId;
        private final boolean toggleResult;

        public boolean getToggleResult() {
            return toggleResult;
        }
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.05
     * 설명 : 즐겨찾기 등록 여부 응답 DTO
     * ---------------------
     * 2024.09.010 양건모 | 기능 구현
     */
    @RequiredArgsConstructor
    @Getter
    public static class hasFavoriteResponseDto {
        private final long parkingLotId;
        private final boolean hasFavorite;

        public boolean getHasFavorite() {
            return hasFavorite;
        }
    }

}
