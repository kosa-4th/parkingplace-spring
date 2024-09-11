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

    @Override
    public InquiryDto.ResponseInquiriesDto getInquiries(Long parkinglotId, Pageable pageable) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkinglotId)
                .orElseThrow(() -> new CustomExceptions.ValidationException("존재하지 않는 주차장입니다."));
        Page<Inquiry> inquiries = inquiryRepository.findByParkingLot(parkingLot, pageable);

        return new InquiryDto.ResponseInquiriesDto(inquiries.hasNext(),
                inquiries.stream().map(InquiryDto.InquiriesDto::new).toList());
    }

    @Override
    public void registerInquiry(User user, Long parkinglotId, String inquiry) {
        // 유저 확인 완
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
}
