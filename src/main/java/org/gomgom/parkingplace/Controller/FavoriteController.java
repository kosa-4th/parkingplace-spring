package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.FavoriteDto;
import org.gomgom.parkingplace.Service.favorite.FavoriteService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
     * 2024.09.11 양건모 | api 명세 변경에 따른 매핑 url 변경
     * */
    @PostMapping("/protected")
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
     * 2024.09.11 양건모 | api 명세 변경에 따른 매핑 url 변경
     * */
    @GetMapping("/parkingLot/{parkingLotId}/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public FavoriteDto.HasFavoriteResponseDto hasFavorite(
            @PathVariable long parkingLotId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return favoriteService.hasFavorite(userDetails.getUser().getId(), parkingLotId);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 즐겨찾기한 주차장 목록 조회
     * @param page 페이지 번호
     * @return 주차장 id, 주차장 이름, 주차장 주소
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * */
    @GetMapping("/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public FavoriteDto.MyFavoritesResponseDto myFavorites(
            @RequestParam long page,
            @PageableDefault(page=0, size=2) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return favoriteService.myFavorites(pageable, userDetails.getUser().getId());
    }

}
