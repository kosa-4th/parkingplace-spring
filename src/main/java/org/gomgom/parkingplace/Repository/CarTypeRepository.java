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

    @Query("SELECT new org.gomgom.parkingplace.Dto.CarTypeDto$CarTypeSelectDto(ct) " +
            "FROM CarType ct")
    List<CarTypeDto.CarTypeSelectDto> getAllCarTypes();
}
