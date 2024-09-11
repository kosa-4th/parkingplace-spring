package org.gomgom.parkingplace.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Configure.CustomUserDetails;
import org.gomgom.parkingplace.Dto.InquiryDto;
import org.gomgom.parkingplace.Service.inquriy.InquiryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
     * @PathVariable: parkinglotId
     * @ModelAttribute: pageable / page, size
     *
     * @return : InquiryDto.getInquiries
     *            - boolean nextPage,
     *            - List<Inquiries> :
     *                  - Long id
     *                  - String inquirer : 문의 작성자 이름
     *                  - String inquiry : 문의 내용
     *                  - String answer : 답변 내용
     *                  - LocalDate inquiryDate : 문의 날짜
     *                  - LocalDate answerDate : 답변 날짜
     */
    @GetMapping()
    public ResponseEntity<?> getInquiries(@PathVariable Long parkingLotId,
                                          Pageable pageable) {
        System.out.println("inquiry Controller-------------");
        System.out.println("parkingLotId: " + parkingLotId);
        return ResponseEntity.ok(inquiryService.getInquiries(parkingLotId, pageable));
    }

    /**
     * 문의 요청하기
     */
    @PostMapping("/protected")
    public ResponseEntity<?> registerInquiry(@PathVariable Long parkingLotId,
                                             @RequestBody InquiryDto.RequestInquiriesDto requestDto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
//        try {
            inquiryService.registerInquiry(userDetails.getUser(), parkingLotId, requestDto.getInquiry());
            return ResponseEntity.ok().build();
//        }catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }
}
