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

    @PostMapping("/verification")
    public ResponseEntity<?> email(@RequestBody UserDto.smtpRequestDto dto) {
        try {
            smtpService.sendVerificationEmail(dto.getEmail());
            return ResponseEntity.ok(new UserDto.smtpResponseDto("인증메일이 발송되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("verify")
    public ResponseEntity<?> verify(@RequestBody UserDto.smtpCodeRequestDto dto) {
        try {
            smtpService.verifyEmail(dto);
            return ResponseEntity.ok(new UserDto.smtpResponseDto("인증되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
