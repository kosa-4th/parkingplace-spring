package org.gomgom.parkingplace.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 작성자: 김경민
 * 시작 일자: 2024.09.05
 * 설명 : 차량 타입 Enum
 *  ---------------------
 * 2024.09.05 김경민 | 기능 구현
 * 2024.09.09 양건모 | 한국어 값을 가져올 수 있도록 kor 필드 생성
 * 2024.09.09 오지수 | 한국어 값을 가져올 수 있도록 getKorName 메서드 생성, carEnum-한국어명 매핑
 * */

public enum CarTypeEnum {
    ALL("모든 차량"),
    SMALL("경차"),
    SEDAN("승용차"),
    SUV("SUV"),
    ELECTRIC("전기차");

    private String kor;

    private CarTypeEnum(String kor) {
        this.kor = kor;
    }

    public String getKor() {
        return kor;
    }

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
