package com.ppi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<TestVO> createFee(@RequestHeader HttpHeaders headers, @RequestBody TestVO testVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testVO);
    }
}
