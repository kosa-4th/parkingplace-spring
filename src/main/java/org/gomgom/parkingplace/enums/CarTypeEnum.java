package org.gomgom.parkingplace.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 작성자: 김경민
 * 시작 일자: 2024.09.05
 * 설명 : 차량 타입 Enum
 *  ---------------------
 * 2024.09.05 김경민 | 기능 구현
 * 2024.09.09 양건모 | 한국어 값을 가져올 수 있도록 kor 필드 생성
 * */

@Getter
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
}
