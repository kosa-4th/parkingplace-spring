package org.gomgom.parkingplace.Service.favorite;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.FavoriteDto;
import org.gomgom.parkingplace.Entity.Favorite;
import org.gomgom.parkingplace.Repository.FavoriteRepository;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final FavoriteRepository favoriteRepository;


    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 사용자 id, 주차장 id로 즐겨찾기 토글
     * @param userId 사용자 id
     * @param parkingLotId 주차장 id
     * @return 주차장 id와 토글결과를 dto로 반환
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * */
    @Override
    public FavoriteDto.FavoriteToggleResponseDto toggleFavorite(long userId, long parkingLotId) {
        Optional<Favorite> favoriteOpt = favoriteRepository.findFavorite(userId, parkingLotId);
        boolean toggle = favoriteOpt.isEmpty();

        if (favoriteOpt.isPresent()) {
            favoriteRepository.delete(favoriteOpt.get());
        } else {
            favoriteRepository.save(new Favorite(
                    userRepository.findById(userId).orElseThrow(),
                    parkingLotRepository.findById(parkingLotId).orElseThrow()
            ));
        }

        return new FavoriteDto.FavoriteToggleResponseDto(parkingLotId, toggle);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 사용자 id, 주차장 id로 즐겨찾기 조회
     * @param userId 사용자 id
     * @param parkingLotId 주차장 id
     * @return 주차장 id와 등록 여부를 dto로 반환
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * */
    public FavoriteDto.HasFavoriteResponseDto hasFavorite(long userId, long parkingLotId) {
        Optional<Favorite> favoriteOpt = favoriteRepository.findFavorite(userId, parkingLotId);
        return new FavoriteDto.HasFavoriteResponseDto(parkingLotId, favoriteOpt.isPresent());
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.10
     * 설명 : 즐겨찾기한 주차장 목록 조회
     * @param userId 사용자 id
     * @return 주차장 id, 주차장 이름, 주차장 주소
     *  ---------------------
     * 2024.09.10 양건모 | 기능 구현
     * */
    public FavoriteDto.MyFavoritesResponseDto myFavorites(long userId) {
        return new FavoriteDto.MyFavoritesResponseDto(favoriteRepository.findFavoritesById(userId));
    }
}
