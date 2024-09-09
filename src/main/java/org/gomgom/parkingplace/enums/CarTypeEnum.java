package org.gomgom.parkingplace.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum CarTypeEnum {
    ALL,
    SMALL,
    SEDAN,
    SUV,
    ELECTRIC;

    public String getKorName() {
        switch (this) {
            case SMALL -> {
                return "승용차";
            }
            case SEDAN -> {
                return "세단";
            }
            case SUV -> {
                return "SUV";
            }
            case ELECTRIC -> {
                return "전기차";
            }
            default -> {
                return "";
            }
        }
    }

    public static CarTypeEnum fromKorName(String korName) {
        return Arrays.stream(CarTypeEnum.values())
                .filter(carTypeEnum -> carTypeEnum.getKorName() != null && carTypeEnum.getKorName().trim().equals(korName.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 차량 타입을 찾을 수 없습니다: " + korName));
    }

    public static List<String> getFilteredCarTypes() {
        return Arrays.stream(CarTypeEnum.values())
                .filter(carTypeEnum -> carTypeEnum != CarTypeEnum.ALL)
                .map(CarTypeEnum::getKorName)
                .toList();
    }
}
