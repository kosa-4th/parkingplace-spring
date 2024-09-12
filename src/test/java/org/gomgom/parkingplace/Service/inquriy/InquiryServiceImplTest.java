package org.gomgom.parkingplace.Service.inquriy;

import org.gomgom.parkingplace.Dto.InquiryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InquiryServiceImplTest {
    @Autowired
    private InquiryServiceImpl inquiryService;

    @Test
    @Transactional
    void getInquiries() {
        Long parkingId = 1L;
        Pageable pageable = PageRequest.of(0, 5);
        InquiryDto.ResponseInquiriesDto inquiries = inquiryService.getInquiries(parkingId, pageable);

        assertNotNull(inquiries);
        assertEquals(5, inquiries.getInquiries().size());
    }
////
//    @Test
//    void registerInquiry() {
//    }
//
//    @Test
//    void modifyInquiry() {
//    }
//
//    @Test
//    void registerAnswer() {
//    }
//
//    @Test
//    void modifyAnswer() {
//    }
}