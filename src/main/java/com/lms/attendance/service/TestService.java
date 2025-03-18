package com.lms.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.attendance.repository.TestMapper;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    // 학생 수 조회 메소드
    public long getStudentCount() {
        return testMapper.getStudentCount();  // Mapper를 통해 학생 수 조회
    }
}
