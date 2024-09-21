package org.gomgom.parkingplace.Service.inquriy;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Dto.InquiryDto;
import org.gomgom.parkingplace.Entity.Inquiry;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Repository.InquiryRepository;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class InquiryServiceImpl implements InquiryService{
    private final InquiryRepository inquiryRepository;
    private final ParkingLotRepository parkingLotRepository;

    /**
     * 작성자: 오지수
     * 2024.09.12 : 주차장 상세 페이지에 문의 목록 반환
     * @param parkinglotId 주차장 id
     * @param pageable 페이지
     * @return 주차장 id에 맞는 문의 목록
     */
    @Override
    public InquiryDto.ResponseInquiriesDto getInquiries(Long parkinglotId, Pageable pageable) {
        // 주차장 여부
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 주차장입니다."));
        // 주차장에 작성된 문의 목록
        Page<Inquiry> inquiries = inquiryRepository.findByParkingLot(parkingLot, pageable);
        // dto로 변환하여 반환
        return new InquiryDto.ResponseInquiriesDto(inquiries.hasNext(),
                inquiries.stream().map(InquiryDto.InquiriesDto::new).toList());
    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 사용자가 특정 주차장에 문의 등록
     * @param user 사용자 정보
     * @param parkinglotId 주차장 id
     * @param inquiry 문의 내용
     */
    @Override
    public void registerInquiry(User user, Long parkinglotId, String inquiry) {
        // 주차장 확인
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 주차장입니다."));
        Inquiry inquiryEntity = Inquiry.builder()
                .user(user)
                .parkingLot(parkingLot)
                .inquiry(inquiry)
                .build();
        inquiryRepository.save(inquiryEntity);
    }

    @Override
    public void modifyInquiry(User user, Long parkinglotId, String inquiry) {

    }

    @Override
    public void registerAnswer(User user, Long parkinglotId, String answer) {

    }

    @Override
    public void modifyAnswer(User user, Long parkinglotId, String answer) {

    }

    /**
     * 작성자: 오지수
     * 2024.09.21: 주차장 관리자 페이지 문의 목록 불러오기
     * @param user
     * @param dto
     * @param pageable
     * @return
     */
    @Override
    public InquiryDto.ParkingInquiryResponseDto getInquiriesByParking(User user, InquiryDto.ParkingInquiryRequestDto dto, Pageable pageable) {
        ParkingLot parkingLot = parkingLotRepository.findById(dto.getParkinglotId())
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 주차장입니다."));
        if (!parkingLot.getUser().getId().equals(user.getId())) {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }

        Page<Inquiry> inquiries = null;
        String actionType = dto.getActionType();
        if (actionType.equals("All")) {
            inquiries = inquiryRepository.findByParkingLotAndInquiryCreatedAtGreaterThanEqualAndInquiryCreatedAtLessThan(parkingLot, dto.getFrom(), dto.getTo().plusDays(1), pageable);
        } else if (actionType.equals("Answered")) {
            inquiries = inquiryRepository.findByParkingLotAndAnswerCreatedAtIsNotNullAndInquiryCreatedAtGreaterThanEqualAndInquiryCreatedAtLessThan(parkingLot, dto.getFrom(), dto.getTo().plusDays(1), pageable);
        } else if (actionType.equals("UnAnswered")) {
            inquiries = inquiryRepository.findByParkingLotAndAnswerCreatedAtIsNullAndInquiryCreatedAtGreaterThanEqualAndInquiryCreatedAtLessThan(parkingLot, dto.getFrom(), dto.getTo().plusDays(1), pageable);
        } else {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }

        return new InquiryDto.ParkingInquiryResponseDto(inquiries.hasNext(), inquiries.getTotalPages(),
                inquiries.stream().map(InquiryDto.ParkingInquiryDto::new).toList());
    }

}
