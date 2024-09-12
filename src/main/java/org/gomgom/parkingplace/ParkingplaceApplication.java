package org.gomgom.parkingplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 스케쥴러 활성화
 * 2024.09.11
 * */
@SpringBootApplication
@EnableJpaAuditing
//@EnableScheduling

public class ParkingplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingplaceApplication.class, args);
	}

}
