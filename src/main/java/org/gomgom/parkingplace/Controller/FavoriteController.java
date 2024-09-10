package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.FavoriteDto;
import org.gomgom.parkingplace.Service.favorite.FavoriteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/protected/favorite")
@PreAuthorize("hasRole('ROLE_USER')")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/toggle")
    public FavoriteDto.FavoriteToggleResponseDto toggleFavorite(
            @RequestParam long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        return favoriteService.toggleFavorite(userDetails.getUser().getId(), parkingLotId);
    }

    @PostMapping("/check")
    public FavoriteDto.hasFavoriteResponseDto hasFavorite(
            @RequestParam long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return favoriteService.hasFavorite(userDetails.getUser().getId(), parkingLotId);
    }

}
