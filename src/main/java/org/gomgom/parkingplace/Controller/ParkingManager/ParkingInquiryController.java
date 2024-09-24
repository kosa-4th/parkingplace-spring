package org.gomgom.parkingplace.Controller.ParkingManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.InquiryDto;
import org.gomgom.parkingplace.Service.inquriy.InquiryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/parking-manager/parkinglots/{parkinglotId}")
@RequiredArgsConstructor
@Log4j2
public class ParkingInquiryController {
    private final InquiryService inquiryService;

    /**
     * 작성자: 오지수
     * 주차장 관리자 / 문의 목록 전체 불러오기
     * @param requestDto
     * @param pageable
     * @param userDetails
     * @return
     */
    @GetMapping("/inquiries/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<InquiryDto.ParkingInquiryResponseDto> getParkingInquiries(@PathVariable Long parkinglotId,
                                                                                    InquiryDto.ParkingInquiryRequestDto requestDto,
                                                                                    Pageable pageable,
                                                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 주차장 관리자 문의 목록 불러오기");
        return ResponseEntity.ok(inquiryService.getInquiriesByParking(userDetails.getUser(), parkinglotId, requestDto, pageable));
    }

    // 문의 상세 정보 가져오기
    // inquiries/{inquiryId}/protected
    @GetMapping("/inquiries/{inquiryId}/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<InquiryDto.ParkingInquiryDetailDto> getParkingInquiryDetails(@PathVariable Map<String, String> path,
                                                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Controller: 주차장 관리자 문의 상세 불러오기");

        System.out.println(path.get("parkinglotId"));
        return ResponseEntity.ok(inquiryService.getInquiryByParking(userDetails.getUser(), Long.parseLong(path.get("parkinglotId")), Long.parseLong(path.get("inquiryId"))));
    }

    // 관리자 문의 답변 등록하기
    //inquiries/protected
    @PostMapping("/inquiries/{inquiryId}/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<Void> registerParkingAnswer(@PathVariable Map<String, String> path,
                                                      @RequestBody InquiryDto.ParkingInquiryAnswerRequest requestDto,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        inquiryService.registerAnswerByParking(userDetails.getUser(), Long.parseLong(path.get("parkinglotId")), Long.parseLong(path.get("inquiryId")), requestDto.getAnswer());
        return ResponseEntity.ok().build();
    }

    // 관리자 문의 답변 수정하기
    // post inquiries/{inquiryId}/protected
    @PutMapping("/inquiries/{inquiryId}/protected")
    @PreAuthorize("hasRole('ROLE_PARKING_MANAGER')")
    public ResponseEntity<Void> modifyParkingAnswer(@PathVariable Map<String, String> path,
                                                    @RequestBody InquiryDto.ParkingInquiryAnswerRequest requestDto,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        inquiryService.modifyAnswerByParking(userDetails.getUser(), Long.parseLong(path.get("parkinglotId")), Long.parseLong(path.get("inquiryId")), requestDto.getAnswer());
        return ResponseEntity.ok().build();
    }


}
