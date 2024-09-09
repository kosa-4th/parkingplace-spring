package org.gomgom.parkingplace.Repository;

import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.enums.CarTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarTypeRepository extends JpaRepository<CarType, Long> {
    CarType findByCarTypeEnum(CarTypeEnum carTypeEnum);
}
