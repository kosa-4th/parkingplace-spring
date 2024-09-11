package org.gomgom.parkingplace.Service.inquriy;

import org.gomgom.parkingplace.Dto.InquiryDto;
import org.gomgom.parkingplace.Entity.User;
import org.springframework.data.domain.Pageable;

public interface InquiryService {
    //문의 목록 가져오기
    InquiryDto.ResponseInquiriesDto getInquiries(Long parkinglotId, Pageable pageable);

    //문의 작성하기
    void registerInquiry(User user, Long parkinglotId, String inquiry);

    //문의 수정하기
    void modifyInquiry(User user, Long parkinglotId, String inquiry);

    //답변 등록하기
    void registerAnswer(User user, Long parkinglotId, String answer);

    //답변 수정하기
    void modifyAnswer(User user, Long parkinglotId, String answer);
}
