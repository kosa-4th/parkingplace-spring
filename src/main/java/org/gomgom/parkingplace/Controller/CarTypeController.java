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

    @GetMapping
    public ResponseEntity<CarTypeDto.AllCarTypeSelectResponseDto> getAllCarTypes() {
        return ResponseEntity.ok(carTypeService.getAllCarTypes());
    }

}
