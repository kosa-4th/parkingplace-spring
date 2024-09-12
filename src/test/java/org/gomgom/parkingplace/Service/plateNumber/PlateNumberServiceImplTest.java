package org.gomgom.parkingplace.Service.plateNumber;

import jakarta.transaction.Transactional;
import org.gomgom.parkingplace.Entity.User;
import org.gomgom.parkingplace.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlateNumberServiceImplTest {

    @Autowired
    private PlateNumberService plateNumberService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void test() {
        User user = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("user email : " + user.getEmail());

        Map<String, Object> map = plateNumberService.getPlateNumber(user);
        System.out.println(map.toString());
    }
}