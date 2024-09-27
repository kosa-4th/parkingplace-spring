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
import org.gomgom.parkingplace.Service.Common.CommonParkingValidService;
import org.gomgom.parkingplace.Service.notification.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class InquiryServiceImpl implements InquiryService{
    private final InquiryRepository inquiryRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final CommonParkingValidService validService;
    private final NotificationService notificationService;

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

    @Override
    public InquiryDto.ResponseInquiryDto getInquiry(Long userId, Long parkinglotId, Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 문의입니다."));
        if (!inquiry.getParkingLot().getId().equals(parkinglotId)) {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }

        if (!inquiry.getUser().getId().equals(userId)) {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }

        return new InquiryDto.ResponseInquiryDto(inquiry);

    }

    /**
     * 작성자: 오지수
     * 2024.09.11 : 사용자가 특정 주차장에 문의 등록
     * @param user 사용자 정보
     * @param parkinglotId 주차장 id
     * @param inquiry 문의 내용
     */
    @Override
    @Transactional
    public void registerInquiry(User user, Long parkinglotId, InquiryDto.RequestInquiriesDto dto) {
        // 주차장 확인
        System.out.println("service단 시작");
        System.out.println("secret: " + dto.getSecret());
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 주차장입니다."));
        Inquiry inquiryEntity = Inquiry.builder()
                .user(user)
                .parkingLot(parkingLot)
                .inquiryDto(dto)
                .build();
        System.out.println("저장된: " +inquiryEntity.getIsSecret().name());
        inquiryRepository.save(inquiryEntity);
    }



    /**
     * 작성자: 오지수
     * 사용자 문의 수정
     * @param user
     * @param parkinglotId
     * @param requestDto
     */
    @Override
    @Transactional
    public void modifyInquiry(User user, Long parkinglotId, Long inquiryId, InquiryDto.RequestInquiryModifyDto requestDto) {
        Inquiry inquiry = vaildInquiry(user, parkinglotId, inquiryId);
        inquiry.modifyInquiry(requestDto.getNewInquiry());
    }

    /**
     * 작성자: 오지수
     * 사용자 문의 삭제
     * @param user
     * @param parkinglotId
     * @param inquiryId
     */
    @Override
    @Transactional
    public void deleteInquiry(User user, Long parkinglotId, Long inquiryId) {
        Inquiry inquiry = vaildInquiry(user, parkinglotId, inquiryId);
        inquiryRepository.delete(inquiry);
    }

    /**
     * 작성자: 오지수
     * 사용자 문의 수정, 삭제를 위한 문의 유효성 검사
     * @param user
     * @param parkinglotId
     * @param inquiryId
     * @return
     */
    private Inquiry vaildInquiry(User user, Long parkinglotId, Long inquiryId) {
        ParkingLot parkingLot = validService.ifExistParkinglot(parkinglotId);
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 문의입니다."));

        if (!inquiry.getParkingLot().getId().equals(parkinglotId)) {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }

        if (!inquiry.getUser().getId().equals(user.getId())) {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }
        return inquiry;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // 관리자
    /**
     * 작성자: 오지수
     * 2024.09.21: 주차장 관리자 페이지 문의 목록 불러오기
     * @param user
     * @param dto
     * @param pageable
     * @return
     */
    @Override
    public InquiryDto.ParkingInquiryResponseDto getInquiriesByParking(User user, Long parkinglotId, InquiryDto.ParkingInquiryRequestDto dto, Pageable pageable) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
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

        return new InquiryDto.ParkingInquiryResponseDto(inquiries.getTotalPages(), inquiries.getNumber(),
                inquiries.stream().map(InquiryDto.ParkingInquiryDto::new).toList());
    }

    /**
     * 작성자: 오지수
     * 주차장 관리자 페이지 : 문의 상세 정보 가져오기
     * @param user
     * @param path / parkinglotId, inquiryId
     * @return
     */

    /**
     * 작성자: 오지수
     * 주차장 관리자 페이지 : 문의 상세 정보 가져오기
     * @param user
     * @param parkinglotId
     * @param inquiryId
     * @return
     */
    @Override
    public InquiryDto.ParkingInquiryDetailDto getInquiryByParking(User user, Long parkinglotId, Long inquiryId) {
        Inquiry inquiry = getInquiryByParking(user.getId(), parkinglotId, inquiryId);
        return new InquiryDto.ParkingInquiryDetailDto(inquiry);
    }

    /**
     * 작성자: 오지수
     * 주차장 관리자 문의 답변 등록하기
     * @param user
     * @param parkinglotId
     * @param inquiryId
     * @param answer
     */
    @Override
    @Transactional
    public void registerAnswerByParking(User user, Long parkinglotId, Long inquiryId, String answer) {
        Inquiry inquiry = getInquiryByParking(user.getId(), parkinglotId, inquiryId);
        inquiry.addAnswer(answer);
        inquiryRepository.save(inquiry);
        notificationService.createNotification(inquiry.getUser().getId(),
                "'" + inquiry.getParkingLot().getName() + "에 문의하신 내용에 대한 답변이 도착했습니다.",
                "/lot/" + parkinglotId + "/inquiry");
    }

    /**
     * 작성자: 오지수
     * 주차장 관리자 문의 답변 수정하기
     * @param user
     * @param parkinglotId
     * @param answerDto
     */

    /**
     * 작성자: 오지수
     * 주차장 관리자 문의 답변 수정하기
     * @param user
     * @param parkinglotId
     * @param inquiryId
     * @param answer
     */
    @Override
    @Transactional
    public void modifyAnswerByParking(User user, Long parkinglotId, Long inquiryId, String answer) {
        Inquiry inquiry = getInquiryByParking(user.getId(), parkinglotId, inquiryId);
        inquiry.modifyAnswer(answer);
    }

    /**
     * 작성자: 오지수
     * 주차장 관리자 문의 등록 및 수정
     * @param userId
     * @param parkinglotId
     * @param inquiryId
     * @return
     */
    private Inquiry getInquiryByParking(Long userId, Long parkinglotId, Long inquiryId) {
        ParkingLot parkingLot = validService.ifValidParkinglotWithUser(parkinglotId, userId);
        Inquiry inquiry =  inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 문의입니다."));
        if(!inquiry.getParkingLot().equals(parkingLot)) {
            throw new CustomExceptions.ValidationException("유효하지 않은 접근입니다.");
        }
        return inquiry;
    }

}
