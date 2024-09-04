package org.gomgom.parkingplace.util;

import java.security.SecureRandom;
import org.gomgom.parkingplace.Repository.ReservationRepository;
import org.springframework.stereotype.Component;

/**
 * CustomUUIDGenerator.java
 * 예약번호 생성
 * @author 김경민
 * @date 2024-09-03
 */
@Component
public class CustomUUIDGenerator {

    private static final String PREFIX = "RV";
    private static final int RANDOM_PART_LENGTH = 10;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    private final ReservationRepository reservationRepository;

    public CustomUUIDGenerator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public String generateUniqueUUID() {
        String uuid;
        do {
            uuid = generateCustomUUID();
        } while (reservationRepository.existsByReservationUuid(uuid));
        return uuid;
    }

    private String generateCustomUUID() {
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}