package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Service.SMTPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SMTPController {
    private final SMTPService smtpService;

    public record MessageRecord(String message, boolean success) {}

    /**
     * 작성자: 오지수
     * ? : 이메일 인증을 위한 요청
     * @param dto / 이메일 정보
     * @return
     */
    @PostMapping("/verification")
    public ResponseEntity<MessageRecord> email(@RequestBody UserDto.smtpRequestDto dto) {
        smtpService.sendVerificationEmail(dto.getEmail());
        return ResponseEntity.ok(new MessageRecord("인증 번호가 발송되었습니다.", true));
    }

    /**
     * 작성자: 오지수
     * ? : 메일 인증 번호 인증
     * @param dto
     * @return
     */
    @PostMapping("verify")
    public ResponseEntity<?> verify(@RequestBody UserDto.smtpCodeRequestDto dto) {
        smtpService.verifyEmail(dto);
        return ResponseEntity.ok(new MessageRecord("인증되었습니다.", true));
    }
}
