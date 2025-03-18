package com.lms.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.attendance.service.TestService;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;

    // DB 연결 확인 API
    @GetMapping("/db")
    public ResponseEntity<String> testDbConnection() {
        try {
            long count = testService.getStudentCount();  // 서비스에서 학생 수 조회
            return ResponseEntity.ok("DB 연결 성공, 학생 수: " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("DB 연결 실패: " + e.getMessage());
        }
    }
}
