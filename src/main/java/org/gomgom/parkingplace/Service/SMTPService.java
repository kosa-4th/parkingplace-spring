package org.gomgom.parkingplace.Service;

import lombok.RequiredArgsConstructor;
import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.Smtp;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Exception.CustomExceptions;
import org.gomgom.parkingplace.Repository.SmtpRepository;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SMTPService {
    private final UserRepository userRepository;
    private final JavaMailSenderImpl mailSender;
    private final SmtpRepository smtpRepository;

    public void sendVerificationEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new CustomExceptions.ValidationException("이미 존재하는 이메일입니다.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        String verificationCode = generateCode();
        smtpRepository.deleteByEmail(email);
        smtpRepository.save(new Smtp(email, verificationCode));

        message.setTo(email);

        String mail = "\n ParkingPlace 회원가입 이메일 인증";
        message.setSubject("회원가입을 위한 이메일 인증번호 메일입니다.");
        message.setText("인증 번호는 [ " + verificationCode + " ]입니다." + mail);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void verifyEmail(UserDto.smtpCodeRequestDto dto) {
        Optional<Smtp> smtp = smtpRepository.findByEmailAndSmtp(dto.getEmail(), dto.getCode());
        if (smtp.isPresent()) {
            smtpRepository.delete(smtp.get());
        } else {
            throw new CustomExceptions.ValidationException("인증번호가 일치하지 않습니다.");
        }
    }

    private String generateCode() {
        Random random = new Random();
        String key = "";

        for (int i = 0; i<3; i++) {
            int index = random.nextInt(26) + 65;
            key += (char)index;
        }
        for (int i = 0; i<6; i++) {
            int index = random.nextInt(10);
            key += index;
        }
        return key;
    }
}
