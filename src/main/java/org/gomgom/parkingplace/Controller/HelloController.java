package org.gomgom.parkingplace.Controller;

import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping()
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().build();
    }

}
