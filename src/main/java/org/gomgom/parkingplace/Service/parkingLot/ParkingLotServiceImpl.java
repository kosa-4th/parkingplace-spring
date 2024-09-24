package org.gomgom.parkingplace.Service.parkingLot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.gomgom.parkingplace.Dto.ParkingLotDto;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.ParkingImage;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.ParkingImageRepository;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.gomgom.parkingplace.enums.Bool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.gomgom.parkingplace.enums.Bool.N;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {
    private final EntityManager em;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingImageRepository parkingImageRepository;
    private final UserRepository userRepository;
    @Value("${parple.upload.virtual}")
    private String uploadPath;


    /**
     * @Author 김경민
     * @Date 2024.09.24
     *
     * 주차장 데이터 수정
     * */
    @Override
    @Transactional
    public int modifyLotData(ParkingLotDto.RequestModifyLotDto requestModifyLotDto) {
        requestModifyLotDto.trimFields();
        ParkingLot parkingLot = parkingLotRepository.findById(requestModifyLotDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 주차장을 찾을 수 없습니다."));

        Bool usable = N;
        if(requestModifyLotDto.getUserEmail()!=null) {
            User user = userRepository.findByEmail(requestModifyLotDto.getUserEmail())
                    .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없음"));

            if(user.getId()!=null){
                usable = Bool.Y;
            }
            parkingLot.setName(requestModifyLotDto.getName());
            parkingLot.setTel(requestModifyLotDto.getTel());
            parkingLot.setParkingType(requestModifyLotDto.getParkingType());
            parkingLot.setLatitude(requestModifyLotDto.getLatitude());
            parkingLot.setLongitude(requestModifyLotDto.getLongitude());
            parkingLot.setWeekdaysOpenTime(requestModifyLotDto.getWeekdaysOpenTime());
            parkingLot.setWeekdaysCloseTime(requestModifyLotDto.getWeekdaysCloseTime());
            parkingLot.setWeekendOpenTime(requestModifyLotDto.getWeekendOpenTime());
            parkingLot.setWeekendCloseTime(requestModifyLotDto.getWeekendCloseTime());
            parkingLot.setWash(requestModifyLotDto.getWash());
            parkingLot.setMaintenance(requestModifyLotDto.getMaintenance());
            parkingLot.setUser(user);
            parkingLot.setUsable(usable);

            parkingLotRepository.save(parkingLot);
            return 1;
        }else {
            parkingLot.setName(requestModifyLotDto.getName());
            parkingLot.setTel(requestModifyLotDto.getTel());
            parkingLot.setParkingType(requestModifyLotDto.getParkingType());
            parkingLot.setLatitude(requestModifyLotDto.getLatitude());
            parkingLot.setLongitude(requestModifyLotDto.getLongitude());
            parkingLot.setWeekdaysOpenTime(requestModifyLotDto.getWeekdaysOpenTime());
            parkingLot.setWeekdaysCloseTime(requestModifyLotDto.getWeekdaysCloseTime());
            parkingLot.setWeekendOpenTime(requestModifyLotDto.getWeekendOpenTime());
            parkingLot.setWeekendCloseTime(requestModifyLotDto.getWeekendCloseTime());
            parkingLot.setWash(requestModifyLotDto.getWash());
            parkingLot.setMaintenance(requestModifyLotDto.getMaintenance());
            parkingLot.setUser(null);
            parkingLot.setUsable(usable);

            parkingLotRepository.save(parkingLot);
            return 1;
        }
    }

    /**
     * @Author 김경민
     * @Date 2024.09.23
     * <p>
     * 주차장 리스트 검색어 처리
     */

    @Override
    @Transactional
    public Page<ParkingLotDto.ResponseParkingLotDto> getParkingLotData(String name, String address, Pageable pageable) {
        Page<ParkingLot> parkingLots = parkingLotRepository.findByNameAndAddress(name, address, pageable);

        return parkingLots.map(parkingLot -> {
            // User가 존재할 경우만 DTO 변환, 없을 경우 null 처리
            UserDto.ResponseAllUserDto userDto = null;
            if (parkingLot.getUser() != null) {
                User user = parkingLot.getUser();
                userDto = new UserDto.ResponseAllUserDto(
                        user.getId(),
                        user.getAuth(),
                        user.getEmail(),
                        user.getName()
                );
            }

            return new ParkingLotDto.ResponseParkingLotDto(
                    parkingLot.getId(),
                    parkingLot.getName(),
                    parkingLot.getAddress(),
                    parkingLot.getTel(),
                    parkingLot.getParkingType(),
                    parkingLot.getLatitude(),
                    parkingLot.getLongitude(),
                    parkingLot.getWeekdaysOpenTime(),
                    parkingLot.getWeekdaysCloseTime(),
                    parkingLot.getWeekendOpenTime(),
                    parkingLot.getWeekendCloseTime(),
                    parkingLot.getWash(),
                    parkingLot.getMaintenance(),
                    parkingLot.getUsable(),
                    userDto  // 주차장 관리자 정보 DTO로 변환
            );
        });
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.02
     * 설명 : 위도 경도 범위 내의 주차장들의 위도, 경도를 포함한 List 정보를 클래스로 한번 감싸 전달
     *
     * @param request 최소 위도, 최대 위도, 최소 경도, 최대 경도
     * @return List 형태로 id, 이름, 위도, 경도, 주소
     * ---------------------
     * 2024.09.05 양건모 | 기능 구현
     */
    @Override
    public ParkingLotDto.ParkingLotMarkersResponseDto getParkingLots(ParkingLotDto.ParkingLotListRequestDto request) {
        List<ParkingLotDto.ParkingLotMarkerDto> markers = parkingLotRepository.getParkingLots(request.getMinLat(), request.getMaxLat(), request.getMinLon(), request.getMaxLon());
        return new ParkingLotDto.ParkingLotMarkersResponseDto(markers);
    }

    /**
     * 작성자: 오지수
     * 2024.09.05 : 주차장 상세 페이지에 전달할 정보
     *
     * @param parkingLotId 주차장 id
     * @return
     */
    @Override
    public ParkingLotDto.ParkingLotDetailResponseDto getParkingLotDetail(Long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow();

        return new ParkingLotDto.ParkingLotDetailResponseDto(parkingLot);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 주차장의 간략한 정보 제공
     *
     * @param parkingLotId 주차장 id
     * @return 주차장명, 주소, 가격, 리뷰 개수, 가장 최신 리뷰, 주차 구역 목록
     * ---------------------
     * 2024.09.07 양건모 | 기능 구현
     */
    @Override
    public ParkingLotDto.ParkingLotPreviewResponseDto getParkingLotPreview(Long parkingLotId) {
        ParkingLotDto.ParkingLotPreviewResponseDto preview = parkingLotRepository.getParkingLotPreviewById(parkingLotId)
                .orElseThrow();

        preview.setParkingSpaces(parkingSpaceRepository.getSpacesPreviewsByParkingLotId(parkingLotId));
        return preview;
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.15
     * 설명 : 사용자 id로 등록된 주차장 조회
     *
     * @param userId 사용자 id
     * @return List 형태로 주차장 id, 주차장 이름
     * ---------------------
     * 2024.09.15 양건모 | 기능 구현
     */
    @Override
    public ParkingLotDto.MyParkingLotsReponseDto getMyParkingLots(Long userId) {
        List<ParkingLotDto.ParkingLotIdAndNameDto> list = parkingLotRepository.findIdAndNameByUserId(userId);
        return new ParkingLotDto.MyParkingLotsReponseDto(list);
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 주차구역, 이미지를 포함한 사용자가 소유한 주차장 상세 정보 조회
     *
     * @param userId       사용자 id
     * @param parkingLotId 주치장 id
     * @return 주차장 상세 정보
     * ---------------------
     * 2024.09.18 양건모 | 기능 구현
     * 2024.09.20 양건모 | 성능 향상을 위해 findById() 메서드를 findByIdIncludeImageSpace() 메서드로 대체
     */
    @Override
    @Transactional(readOnly = true)
    public ParkingLotDto.OwnerParkingLotDetailResponseDto getOwnerParkingLotDetail(long userId, long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findByIdIncludeImageSpace(userId, parkingLotId).orElseThrow();

        //주차장 이미지 가공
        List<ParkingLotDto.MyParkingLotImageDto> images = new ArrayList<>();
        for (ParkingImage image : parkingLot.getParkingImages()) {
            images.add(new ParkingLotDto.MyParkingLotImageDto(image.getId(), uploadPath + File.separator + image.getThumbnailPath()));
        }

        //주차구역 가공
        List<ParkingLotDto.MyParkingLotSpaceDto> parkingSpaces = new ArrayList<>();
        for (ParkingSpace space : parkingLot.getParkingSpaces()) {
            parkingSpaces.add(new ParkingLotDto.MyParkingLotSpaceDto(
                    space.getId(), space.getSpaceName(), space.getCarType().getCarTypeEnum().getKor(),
                    space.getWeekdaysPrice(), space.getWeekendPrice(), space.getWeekAllDayPrice(),
                    space.getWeekendAllDayPrice(), space.getWashPrice(), space.getMaintenancePrice(),
                    space.getAvailableSpaceNum()));
        }

        return new ParkingLotDto.OwnerParkingLotDetailResponseDto(
                parkingLot.getName(), parkingLot.getAddress(), parkingLot.getTel(), parkingLot.getWeekdaysOpenTime(),
                parkingLot.getWeekdaysCloseTime(), parkingLot.getWeekendOpenTime(), parkingLot.getWeekendCloseTime(), images, parkingSpaces
        );
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.18
     * 설명 : 이미지를 포함한 주차장 정보 수정
     *
     * @param userId       사용자 id
     * @param parkingLotId 주차장 id
     * @param request      수정 값
     * @return void
     * ---------------------
     * 2024.09.19 양건모 | 이미지 삭제를 제외한 기능 구현
     * 2024.09.20 양건모 | 이미지 삭제 로직 연결
     * 2024.09.20 양건모 | 성능 향상을 위해 findById() 메서드를 findByIdIncludeImageSpace() 메서드로 대체
     */
    @Override
    @Transactional
    public void modifyOwnerParkingLotDetail(long userId, long parkingLotId, ParkingLotDto.ParkingLotModifyRequestDto request) throws IOException {
        ParkingLot parkingLot = parkingLotRepository.findByIdIncludeImageSpace(userId, parkingLotId).orElseThrow();

        //기본 정보 수정
        parkingLot.setName(request.getName());
        parkingLot.setTel(request.getTel());
        parkingLot.setWeekdaysOpenTime(request.getWeekdaysOpenTime());
        parkingLot.setWeekdaysCloseTime(request.getWeekdaysCloseTime());
        parkingLot.setWeekendOpenTime(request.getWeekendOpenTime());
        parkingLot.setWeekendCloseTime(request.getWeekendCloseTime());

        //이미지 삭제
        parkingImageRepository.deleteAllById(request.getDeleteImageIds());
        em.flush();

        //이미지 추가
        inputImages(parkingLot, request.getImages());
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.19
     * 설명 : 이미지 삽입
     *
     * @param parkingLot     이미지를 삽입할 주차장 엔티티
     * @param multipartFiles 삽입할 이미지
     * @return void
     * ---------------------
     * 2024.09.19 양건모 | 기능 구현
     */
    @Transactional
    private void inputImages(ParkingLot parkingLot, MultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles != null && multipartFiles.length > 0) {
            String folderPath = makeFolder();
            List<ParkingImage> images = new ArrayList<>();

            for (int i = 0; i < multipartFiles.length; i++) {
                if (!multipartFiles[i].getContentType().startsWith("image") || multipartFiles[i].isEmpty()) {
                    continue;
                }

                String originalName = multipartFiles[i].getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = folderPath + File.separator + uuid + "_" + originalName;
                Path savePath = Paths.get(uploadPath + File.separator + saveName);

                multipartFiles[i].transferTo(savePath);
                String thumbnailSaveName = folderPath + File.separator + "s_" + uuid + "_" + originalName;
                File thumbnailFile = new File(uploadPath + File.separator + thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 300, 300);
                ParkingImage image = new ParkingImage(saveName, thumbnailSaveName, parkingLot);
                images.add(image);
                parkingLot.getParkingImages().add(image);

            }

            parkingImageRepository.saveAll(images);
        }
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.19
     * 설명 : 폴더 생성
     *
     * @return 파일 경로
     * ---------------------
     * 2024.09.19 양건모 | 기능 구현
     */
    private String makeFolder() throws FileNotFoundException {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);
        if (!uploadPathFolder.exists() && !uploadPathFolder.mkdirs()) {
            throw new FileNotFoundException("폴더 생성 실패");
        }

        return folderPath;
    }

}
