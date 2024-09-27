package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.InquiryDto;
import org.gomgom.parkingplace.Service.inquriy.InquiryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkingLots/{parkingLotId}/inquiries")
public class InquiryController {
    private final InquiryService inquiryService;

    /**
     * 작성자: 오지수
     * 2024.09.11 : 문의 목록 불러오기
     * @param parkingLotId
     * @param pageable / page, size
     *
     * @return 다음 페이지 여부와 문의 내용 / InquiryDto.getInquiries
     * ---------------------------------
     */
    @GetMapping()
    public ResponseEntity<InquiryDto.ResponseInquiriesDto> getInquiries(@PathVariable Long parkingLotId,
                                                                        @PageableDefault(sort = "inquiryCreatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(inquiryService.getInquiries(parkingLotId, pageable));
    }

    @GetMapping("/{inquiryId}/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<InquiryDto.ResponseInquiryDto> getInquiry(@PathVariable Long parkingLotId,
                                                                    @PathVariable Long inquiryId,
                                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(inquiryService.getInquiry(userDetails.getUser().getId(), parkingLotId, inquiryId));
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 사용자가 문의 작성하기
     * @param parkingLotId
     * @param requestDto / 문의 내용
     * @param userDetails / 사용자 정보
     * @return 성공, 에러 여부
     */
    @PostMapping("/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> registerInquiry(@PathVariable Long parkingLotId,
                                             @RequestBody InquiryDto.RequestInquiriesDto requestDto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("Controller");
        System.out.println("새로 작성한 문의: " + requestDto.getInquiry());
        System.out.println("secret 여부 : " + requestDto.getSecret());
        inquiryService.registerInquiry(userDetails.getUser(), parkingLotId, requestDto);
        return ResponseEntity.noContent().build();
    }

    //수정
    @PutMapping("/{inquiryId}/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> modifyInquiry(@PathVariable Long parkingLotId,
                                              @PathVariable Long inquiryId,
                                              @RequestBody InquiryDto.RequestInquiryModifyDto requestDto,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        inquiryService.modifyInquiry(userDetails.getUser(), parkingLotId, inquiryId, requestDto);
        return ResponseEntity.noContent().build();
    }

    //삭제
    @DeleteMapping("/{inquiryId}/protected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long parkingLotId,
                                              @PathVariable Long inquiryId,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        inquiryService.deleteInquiry(userDetails.getUser(), parkingLotId, inquiryId);
        return ResponseEntity.noContent().build();
    }
}
