package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.PlateNumberDto;
import org.gomgom.parkingplace.Service.plateNumber.PlateNumberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/cars")
public class PlateNumberController {
    private final PlateNumberService plateNumberService;

    /**
     * 작성자: 오지수
     * 2024.08.10 : 마이페이지에서 내 차 목록 불러오기
     * @param userDetails / 사용자 정보
     * @return carTypes: 하단 새 차량 등록을 위해 필요한 차량 종류 목록
     *         myCars: 현재 내 차량 목록
     */
    @GetMapping("/protected")
    public ResponseEntity<Map<String, Object>> getCars(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Map<String, Object> result = plateNumberService.getPlateNumber(userDetails.getUser());
        return ResponseEntity.ok(result);
    }

    /**
     * 작성자: 오지수
     * 2024.09.10 : 새 차량 등록하기
     * @param myCarDto / 차량 번호, 차량 종류
     * @param userDetails / 사용자 정보
     * @return / 생각해봐야함
     */
    @PostMapping("/protected")
    public ResponseEntity<Void> registerPlateNumber(@RequestBody PlateNumberDto.RequestMyCarDto myCarDto,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        plateNumberService.registerPlateNumber(userDetails.getUser(), myCarDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 작성자: 오지수
     * 2024.09.10 : 등록된 차량 삭제하기
     * @param carId / 삭제할 차량 id
     * @param myCarDto / 차량 정보 : 차량 번호, 차 종류
     * @param userDetails /사용자 정보
     * @return
     */
    @DeleteMapping("/{carId}/protected")
    public ResponseEntity<Void> unregisterPlateNumber(@PathVariable String carId,
                                                   @RequestBody PlateNumberDto.RequestMyCarDto myCarDto,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        plateNumberService.deletePlateNumber(userDetails.getUser(), myCarDto);
        return ResponseEntity.noContent().build();
    }
}
