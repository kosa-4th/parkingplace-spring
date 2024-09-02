package org.gomgom.parkingplace.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestController {

    @GetMapping("/api/test")
    public List<String> testapi() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("test");
        list.add("aaaa");
        return list;
    }
}
