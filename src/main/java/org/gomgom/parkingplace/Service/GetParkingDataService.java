package org.gomgom.parkingplace.Service;//package org.gomgom.parkingplace.Service;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.gomgom.parkingplace.Entity.CarType;
//import org.gomgom.parkingplace.Entity.ParkingLot;
//import org.gomgom.parkingplace.Entity.ParkingSpace;
//import org.gomgom.parkingplace.Repository.CarTypeRepository;
//import org.gomgom.parkingplace.Repository.ParkingLotRepository;
//import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import java.io.InputStream;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Log4j2
///*
//작성자: 오지수
//주차장 데이터
// */
//public class GetParkingDataService {
//
//    private final ParkingLotRepository parkingLotRepository;
//    private final ParkingSpaceRepository parkingSpaceRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final CarTypeRepository carTypeRepository;
//
////    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void getParkingApi() {
//        try {
//            log.info("add parking api data ===========================================================================");
//            // ClassPathResource를 사용하여 resources 폴더에 있는 JSON 파일을 읽음
//            ClassPathResource resource = new ClassPathResource("SeoulParkingInfo.json");
//            InputStream inputStream = resource.getInputStream();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
//            CarType cartype = carTypeRepository.findById(1L)
//                    .orElseThrow(() -> new RuntimeException("Car type not found"));
//            LocalTime localTime = LocalTime.parse("0000", formatter);
//
//            //Json 파일을 Java 객체로 변환
//            Map<String, Object> jsonData  = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
//            List<Map<String, Object>> data = (List<Map<String, Object>>) jsonData.get("DATA");
//            int num = 0;
//            // 데이터 저장 처리
//            for (Map<String, Object> entry : data) {
//                log.info("진행중: " + ++num);
//                log.info(entry.toString());
//                // parkingCode가 null인 경우 continue
//                String parkingCode = Optional.ofNullable(entry.get("pklt_cd"))
//                        .map(Object::toString)
//                        .orElse(null).trim();
//                if (parkingCode == null) {
//                    continue; // parkingCode가 없으면 처리하지 않고 넘어감
//                }
//
//                // 각 필드를 Optional 또는 null 체크하여 변환
//                String parkingName = Optional.ofNullable(entry.get("pklt_nm"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                String address = Optional.ofNullable(entry.get("addr"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                String parkingType = Optional.ofNullable(entry.get("pklt_knd_nm"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                String parkingTel = Optional.ofNullable(entry.get("telno"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                int availability = Optional.ofNullable(entry.get("tpkct"))
//                        .map(Object::toString)
//                        .map(Integer::parseInt)
//                        .orElse(0);
//
//                // 시간 데이터를 LocalTime으로 변환
//                String weeksOpenTime = Optional.ofNullable(entry.get("wd_oper_bgng_tm"))
//                        .map(Object::toString)
//                        .orElse("0000").trim(); // 기본값 "0000"
//                String weeksCloseTime = Optional.ofNullable(entry.get("wd_oper_end_tm"))
//                        .map(Object::toString)
//                        .orElse("0000").trim(); // 기본값 "0000"
//                String weekendOpenTime = Optional.ofNullable(entry.get("we_oper_bgng_tm"))
//                        .map(Object::toString)
//                        .orElse("0000").trim(); // 기본값 "0000"
//                String weekendCloseTime = Optional.ofNullable(entry.get("we_oper_end_tm"))
//                        .map(Object::toString)
//                        .orElse("0000").trim(); // 기본값 "0000"
//
//                // DateTimeFormatter를 사용하여 시간 변환
//
//
//                LocalTime weeksOpen = localTime;
//                LocalTime weeksClose = localTime;
//                LocalTime weekendOpen = localTime;
//                LocalTime weekendClose = localTime;
//                try {
//                    weeksOpen = LocalTime.parse(weeksOpenTime, formatter);
//                } catch (Exception ignored) {
//                }
//                try {
//                    weeksClose = LocalTime.parse(weeksCloseTime, formatter);
//                } catch (Exception ignored) {
//                }
//                try {
//                    weekendOpen = LocalTime.parse(weekendOpenTime, formatter);
//                } catch (Exception ignored) {
//                }
//                try {
//                    weekendClose = LocalTime.parse(weekendCloseTime, formatter);
//                } catch (Exception ignored) {
//                }
//
//
//                // 가격 정보를 처리
//                int basicPrice = Optional.ofNullable(entry.get("prk_crg"))
//                        .map(Object::toString)
//                        .map(Integer::parseInt)
//                        .orElse(0);
//                int priceMin = Optional.ofNullable(entry.get("prk_hm"))
//                        .map(Object::toString)
//                        .map(Integer::parseInt)
//                        .orElse(1); // 0으로 나누는 오류 방지
//
//                // 기본 가격을 30분 단위로 조정
//                basicPrice = basicPrice / (priceMin == 0 ? 1 : priceMin) * 30;
//
//                int plusPrice = Optional.ofNullable(entry.get("add_crg"))
//                        .map(Object::toString)
//                        .map(Integer::parseInt)
//                        .orElse(0);
//                int plusPriceMin = Optional.ofNullable(entry.get("add_unit_tm_mnt"))
//                        .map(Object::toString)
//                        .map(Integer::parseInt)
//                        .orElse(0);
//
//                int dayPrice = Optional.ofNullable(entry.get("dly_max_crg"))
//                        .map(Object::toString)
//                        .map(Integer::parseInt)
//                        .orElse(0);
//
//                // 위도 및 경도 정보 처리
//                Double lat = Optional.ofNullable(entry.get("lat"))
//                        .map(Object::toString)
//                        .map(Double::parseDouble)
//                        .orElse((double) -1); // null일 수 있음
//                Double lon = Optional.ofNullable(entry.get("lot"))
//                        .map(Object::toString)
//                        .map(Double::parseDouble)
//                        .orElse((double)-1); // null일 수 있음
//
//
//                // 주차장 코드로 기존 데이터 조회후 저장
//                String finalParkingCode = parkingCode;
//                LocalTime finalWeeksOpen = weeksOpen;
//                LocalTime finalWeeksClose = weeksClose;
//                LocalTime finalWeekendOpen = weekendOpen;
//                LocalTime finalWeekendClose = weekendClose;
//                ParkingLot parkingLot = parkingLotRepository.findByParkingCenterId(parkingCode)
//                        .orElseGet(() -> {
//                            ParkingLot newLot = ParkingLot.builder()
//                                    .name(parkingName)
//                                    .address(address)
//                                    .tel(parkingTel)
//                                    .parkingType(parkingType)
//                                    .parkingCenterId(parkingCode)
//                                    .longitude(lon)
//                                    .latitude(lat)
//                                    .weekdaysOpenTime(finalWeeksOpen)
//                                    .weekdaysCloseTime(finalWeeksClose)
//                                    .weekendOpenTime(finalWeekendOpen)
//                                    .weekendCloseTime(finalWeekendClose)
//                                    .build();
//                            ParkingLot lot = parkingLotRepository.save(newLot);
//                            return lot;
//                        });
//
//                //주차 공간 정보 저장
//                Optional<ParkingSpace> parkingSpace = parkingSpaceRepository.findByParkingLot(parkingLot);
//                if (parkingSpace.isPresent()) {
//                    parkingSpace.get().setPlusAvailableSpaceNum(availability);
//                    parkingSpaceRepository.save(parkingSpace.get());
//                } else {
//                    ParkingSpace newParkingSpace = ParkingSpace.builder()
//                            .parkingLot(parkingLot)
//                            .carType(cartype)
//                            .availableSpaceNum(availability)
//                            .weekdaysPrice(basicPrice)
//                            .weekAllDayPrice(dayPrice)
//                            .weekendPrice(basicPrice + 1000)
//                            .weekendAllDayPrice(dayPrice + 5000)
//                            .build();
//                    parkingSpaceRepository.save(newParkingSpace);
//                }
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gomgom.parkingplace.Entity.CarType;
import org.gomgom.parkingplace.Entity.ParkingLot;
import org.gomgom.parkingplace.Repository.CarTypeRepository;
import org.gomgom.parkingplace.Repository.ParkingLotRepository;
import org.gomgom.parkingplace.Repository.ParkingSpaceRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetParkingDataService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CarTypeRepository carTypeRepository;

//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void getParkingApi() {
//        try {
//            log.info("add parking api data ===========================================================================");
//            ClassPathResource resource = new ClassPathResource("SeoulParkingInfo.json");
//            InputStream inputStream = resource.getInputStream();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
//            CarType cartype = carTypeRepository.findById(1L)
//                    .orElseThrow(() -> new RuntimeException("Car type not found"));
//            LocalTime localTime = LocalTime.parse("0000", formatter);
//
//            Map<String, Object> jsonData  = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
//            List<Map<String, Object>> data = (List<Map<String, Object>>) jsonData.get("DATA");
//            int num = 0;
//
//            for (Map<String, Object> entry : data) {
//                log.info("진행중: " + ++num);
//                log.info(entry.toString());
//
//                String parkingCode = Optional.ofNullable(entry.get("pklt_cd"))
//                        .map(Object::toString)
//                        .orElse(null).trim();
//                if (parkingCode == null) {
//                    continue;
//                }
//
//                String parkingName = Optional.ofNullable(entry.get("pklt_nm"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                String address = Optional.ofNullable(entry.get("addr"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                String parkingType = Optional.ofNullable(entry.get("pklt_knd_nm"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                String parkingTel = Optional.ofNullable(entry.get("telno"))
//                        .map(Object::toString)
//                        .orElse("").trim();
//                int availability = Optional.ofNullable(entry.get("tpkct"))
//                        .map(Object::toString)
//                        .map(Integer::parseInt)
//                        .orElse(0);
//
//                // 시간 변환 코드 생략...
//
//                Double lat = Optional.ofNullable(entry.get("lat"))
//                        .map(Object::toString)
//                        .map(Double::parseDouble)
//                        .orElse(0.0); // 기본값 0.0
//                Double lon = Optional.ofNullable(entry.get("lot"))
//                        .map(Object::toString)
//                        .map(Double::parseDouble)
//                        .orElse(0.0); // 기본값 0.0
//
//                // 위도와 경도가 0.0이거나 null일 경우 지오코딩 수행
//                if (lat == 0.0 || lon == 0.0) {
//                    try {
//                        log.info(" =======위도 경도 없음========== " +address);
//                        double[] coordinates = geocodingService.getCoordinates(address);
//                        lat = coordinates[0];
//                        lon = coordinates[1];
//                        log.info("Geocoding successful: lat=" + lat + ", lon=" + lon);
//                    } catch (IOException e) {
//                        log.error("Geocoding failed for address: " + address);
//                    }
//                }
//
//                // 주차장 코드로 기존 데이터 조회후 저장
//                Double finalLon = lon;
//                Double finalLat = lat;
//                ParkingLot parkingLot = parkingLotRepository.findByParkingCenterId(parkingCode)
//                        .orElseGet(() -> {
//                            ParkingLot newLot = ParkingLot.builder()
//                                    .name(parkingName)
//                                    .address(address)
//                                    .tel(parkingTel)
//                                    .parkingType(parkingType)
//                                    .parkingCenterId(parkingCode)
//                                    .longitude(finalLon)
//                                    .latitude(finalLat)
//                                    .weekdaysOpenTime(localTime)
//                                    .weekdaysCloseTime(localTime)
//                                    .weekendOpenTime(localTime)
//                                    .weekendCloseTime(localTime)
//                                    .build();
//                            return parkingLotRepository.save(newLot);
//                        });
//
//                // 주차 공간 정보 처리 생략...
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
