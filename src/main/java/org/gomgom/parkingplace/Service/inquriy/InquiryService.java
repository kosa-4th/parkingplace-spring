package org.gomgom.parkingplace.Service.inquriy;

import org.gomgom.parkingplace.Dto.InquiryDto;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface InquiryService {
    //문의 목록 가져오기
    InquiryDto.ResponseInquiriesDto getInquiries(Long parkinglotId, Pageable pageable);

    //문의 1개 가져오기
    InquiryDto.ResponseInquiryDto getInquiry(Long userId, Long parkinglotId, Long inquiryId);

    //문의 작성하기
    void registerInquiry(User user, Long parkinglotId, InquiryDto.RequestInquiriesDto inquiry);

    //문의 수정하기
    void modifyInquiry(User user, Long parkinglotId, Long inquiryId, InquiryDto.RequestInquiryModifyDto requestDto);

    //문의 삭제하기
    void deleteInquiry(User user, Long parkinglotId, Long inquiryId);

    // 주차장 관리자 페이지 문의 목록 가져오기
    InquiryDto.ParkingInquiryResponseDto getInquiriesByParking(User user, Long parkinglotId, InquiryDto.ParkingInquiryRequestDto dto, Pageable pageable);

    // 주차장 관리자 페이지 문의 1개 가져오기
    InquiryDto.ParkingInquiryDetailDto getInquiryByParking(User user, Long parkinglotId, Long inquiryId);

    //주차장 관리자 페이지 문의 답변 등록하기
    void registerAnswerByParking(User user, Long parkinglotId, Long inquiryId, String answer);

    //주차장 관리자 페이지 문의 수정까지 해야할까?
    void modifyAnswerByParking(User user, Long parkinglotId, Long inquiryId, String answer);

}
