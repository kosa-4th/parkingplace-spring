package org.gomgom.parkingplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ParkingplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingplaceApplication.class, args);
	}

}
