package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.CarTypeDto;
import org.gomgom.parkingplace.Service.carType.CarTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/car-type")
@RequiredArgsConstructor
public class CarTypeController {

    private final CarTypeService carTypeService;

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 모든 차량 유형에 대해 id와 유형명 반환
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * */
    @GetMapping
    public ResponseEntity<CarTypeDto.AllCarTypeSelectResponseDto> getAllCarTypes() {
        return ResponseEntity.ok(carTypeService.getAllCarTypes());
    }

}
