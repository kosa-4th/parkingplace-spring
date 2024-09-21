package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Dto.CarTypeDto;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.enums.CarTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarTypeRepository extends JpaRepository<CarType, Long> {
    CarType findByCarTypeEnum(CarTypeEnum carTypeEnum);

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.20
     * 설명 : 모든 차량 유형에 대해 id와 유형명 반환
     *  ---------------------
     * 2024.09.20 양건모 | 기능 구현
     * */
    @Query("SELECT new org.gomgom.parkingplace.Dto.CarTypeDto$CarTypeSelectDto(ct) " +
            "FROM CarType ct")
    List<CarTypeDto.CarTypeSelectDto> getAllCarTypes();
}
