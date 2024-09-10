package org.gomgom.parkingplace.Service.favorite;

import org.gomgom.parkingplace.Dto.FavoriteDto;

import java.util.List;

public interface FavoriteService {
    FavoriteDto.FavoriteToggleResponseDto toggleFavorite(long userId, long parkingLotId);
    FavoriteDto.HasFavoriteResponseDto hasFavorite(long userId, long parkingLot);
    FavoriteDto.MyFavoritesResponseDto myFavorites(long userId);
}
