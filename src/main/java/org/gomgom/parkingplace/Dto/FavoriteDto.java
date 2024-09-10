package org.gomgom.parkingplace.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FavoriteDto {

    @RequiredArgsConstructor
    @Getter
    public static class FavoriteToggleResponseDto {
        private final long parkingLotId;
        private final boolean toggleResult;

        public boolean getToggleResult() {
            return toggleResult;
        }
    }

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
