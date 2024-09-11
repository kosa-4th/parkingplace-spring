package org.gomgom.parkingplace.Service.favorite;

import org.gomgom.parkingplace.Dto.FavoriteDto;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {
    FavoriteDto.FavoriteToggleResponseDto toggleFavorite(long userId, long parkingLotId);
    FavoriteDto.HasFavoriteResponseDto hasFavorite(long userId, long parkingLot);
    FavoriteDto.MyFavoritesResponseDto myFavorites(Pageable pageable, long userId);
}
