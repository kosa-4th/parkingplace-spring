package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.FavoriteDto;
import org.gomgom.parkingplace.Service.favorite.FavoriteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 즐겨찾기 토글(등록/해제)
     * @param parkingLotId 주차장 id
     * @param userDetails 회원 정보
     * @return 주차장 번호와 토글 결과 반환
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * */
    @PostMapping("/toggle/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public FavoriteDto.FavoriteToggleResponseDto toggleFavorite(
            @RequestParam long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        return favoriteService.toggleFavorite(userDetails.getUser().getId(), parkingLotId);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 즐겨찾기 등록 여부 제공
     * @param parkingLotId 주차장 id
     * @param userDetails 회원 정보
     * @return 주차장 번호와 즐겨찾기 등록 여부 반환
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * */
    @GetMapping("/check/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public FavoriteDto.HasFavoriteResponseDto hasFavorite(
            @RequestParam long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return favoriteService.hasFavorite(userDetails.getUser().getId(), parkingLotId);
    }

}
