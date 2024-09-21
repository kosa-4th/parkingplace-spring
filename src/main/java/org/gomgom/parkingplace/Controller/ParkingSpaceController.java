package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.ParkingSpaceDto;
import org.gomgom.parkingplace.Entity.ParkingSpace;
import org.gomgom.parkingplace.Service.parkingSpace.ParkingSpaceServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceServiceImpl parkingSpaceService;

    @PostMapping("/api/parking-manager/info/parkingarea")
    public ResponseEntity<?> insertParkingSpace(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ParkingSpaceDto.InsertParkingSpaceRequestDto request
            ) throws BadRequestException {
        parkingSpaceService.insertParkingSpace(userDetails.getUser().getId(), request);
        return ResponseEntity.ok().build();
    }
}
