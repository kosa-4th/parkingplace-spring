package org.gomgom.parkingplace.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.gomgom.parkingplace.Entity.PlateNumber;

public class PlateNumberDto {

    /**
     * 작성자: 오지수
     * 2024.09.10 : 마이페이지에 내 차량 목록을 전달하기 위해 사용
     */
    @Getter
    public static class ResponseMyCarDto {
        private final Long id; // 등록된 차량 id
        private final String carType; // 차량 종류
        private final String plateNumber; // 차량 번호

        public ResponseMyCarDto(PlateNumber plateNumber) {
            this.id = plateNumber.getId();
            this.carType = plateNumber.getCarType().getCarTypeEnum().getKor();
            this.plateNumber = plateNumber.getPlateNumber();
        }
    }

    /**
     * 작성자: 오지수
     * 2024.09.10 : 차량 등록이나 삭제를 위해 전달하는 정보
     */
    @Data
    @Getter
    public static class RequestMyCarDto {
        private String carType; // 차량 종류

        @NotNull(message = "차량 번호를 입력해주세요.")
        private String plateNumber; // 차량 번호
    }
}
