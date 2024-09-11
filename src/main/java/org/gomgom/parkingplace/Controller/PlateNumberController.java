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

    @GetMapping("/protected")
    public ResponseEntity<?> getCars(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Map<String, Object> result = plateNumberService.getPlateNumber(userDetails.getUser());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/protected")
    public ResponseEntity<?> registerPlateNumber(@RequestBody PlateNumberDto.RequestMyCarDto myCarDto,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println(myCarDto.getPlateNumber());
        System.out.println(userDetails.getUser().getEmail());
        plateNumberService.registerPlateNumber(userDetails.getUser(), myCarDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{carId}/protected")
    public ResponseEntity<?> unregisterPlateNumber(@PathVariable String carId,
                                                   @RequestBody PlateNumberDto.RequestMyCarDto myCarDto,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println(carId);
        System.out.println(myCarDto.getPlateNumber() + myCarDto.getCarType());
        System.out.println(userDetails.getUser().getEmail());
        plateNumberService.deletePlateNumber(userDetails.getUser(), myCarDto);
        return ResponseEntity.ok().build();
    }
}
