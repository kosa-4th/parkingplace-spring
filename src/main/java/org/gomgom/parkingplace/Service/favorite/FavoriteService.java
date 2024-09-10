package org.gomgom.parkingplace.Service.favorite;

import org.gomgom.parkingplace.Dto.FavoriteDto;

public interface FavoriteService {
    FavoriteDto.FavoriteToggleResponseDto toggleFavorite(long userId, long parkingLotId);
    FavoriteDto.hasFavoriteResponseDto hasFavorite(long userId, long parkingLot);
}
