package org.gomgom.parkingplace.Service;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;

import java.io.IOException;

@Log4j2
public class GeocodingService {
    private static final String API_KEY = "AIzaSyDjyGhxq6ESj9ytdmPm8tyCrnBTUwLf0Qc"; // 여기에 Google API 키를 넣으세요.
    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    private final OkHttpClient httpClient = new OkHttpClient();

    public double[] getCoordinates(String address) throws IOException {
        // 주소가 null이거나 빈 문자열일 경우 -1을 반환
        if (address == null || address.trim().isEmpty()) {
            return new double[]{-1.0, -1.0};
        }
        String fullAddress = address + ", 대한민국 서울시";

        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(fullAddress).setLanguage("ko")
//                .setKey(API_KEY)
                .getGeocoderRequest();

//        try {
            Geocoder geocoder = new Geocoder();
            GeocodeResponse geocodeResponse = geocoder.geocode(geocoderRequest);
            log.info("----------------------------geocodeResponse");
            log.info(geocodeResponse.getStatus());
            log.info(geocodeResponse.getResults());
            if (geocodeResponse.getStatus() == GeocoderStatus.OK & !geocodeResponse.getResults().isEmpty()) {
                GeocoderResult geocoderResult = geocodeResponse.getResults().iterator().next();
                LatLng latLng = geocoderResult.getGeometry().getLocation();

                double[] latlngs = new double[2];
                latlngs[0] = latLng.getLat().doubleValue();
                latlngs[1] = latLng.getLng().doubleValue();
                return latlngs;
            } else {
                return new double[]{-1.0, -1.0};
            }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new double[] {-1.0, -1.0};
    }
}
