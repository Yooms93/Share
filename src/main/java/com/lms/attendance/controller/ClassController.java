package com.lms.attendance.controller;

import com.lms.attendance.model.ClassDetail;
import com.lms.attendance.model.ClassSettings;
import com.lms.attendance.model.Classroom;
import com.lms.attendance.service.ClassService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/user/{userId}")
    public List<Classroom> getUserClasses(@PathVariable("userId") String userId) {
        logger.info("📌 [DEBUG] 강의실 목록 조회 요청 도착: userId={}", userId);

        List<Classroom> classes = classService.getClassesByUserId(userId);

        logger.info("📌 [DEBUG] 조회된 강의실 개수: {}", classes.size());
        logger.info("📌 [DEBUG] 조회된 강의실 리스트: {}", classes);  // ✅ 리스트 전체 출력

        return classes;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Classroom>> getAllClasses() {
        List<Classroom> classes = classService.getAllClasses();
        System.out.println("📌 불러온 강의실 목록: " + classes); // ✅ 콘솔 로그 확인
        return ResponseEntity.ok(classes);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addClassroom(@RequestBody Classroom newClass) {
        classService.addClassroom(newClass);
        return ResponseEntity.ok("강의실 추가 완료");
    }
    
 // ✅ 강의실 삭제 API 추가
    @DeleteMapping("/{classId}")
    public ResponseEntity<?> deleteClass(@PathVariable("classId") int classId) {
        classService.deleteClassById(classId);
        return ResponseEntity.ok("강의실 삭제 성공");
    }
    
 // ✅ 특정 강의실의 출석 시간 설정 가져오기
    @GetMapping("/{classId}/settings")
    public ResponseEntity<?> getClassSettings(@PathVariable("classId") int classId) {  // ✅ @PathVariable에 이름 명시
        ClassSettings settings = classService.getClassSettingsById(classId);
        if (settings != null) {
            return ResponseEntity.ok(settings);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강의실 출석 시간 설정을 찾을 수 없습니다.");
        }
    }
    
 // ✅ 출석 시간 설정 저장 API 추가
    @PutMapping("/{classId}/settings")
    public ResponseEntity<?> updateClassSettings(@PathVariable("classId") int classId, @RequestBody ClassSettings settings) {
        settings.setClassId(classId);  // ✅ URL에서 받은 classId를 객체에 설정
        classService.updateClassSettings(settings);
        return ResponseEntity.ok("✅ 출석 시간이 업데이트되었습니다.");
    }
    
    //classId로 요청이 들어왔을 때 디테일 객체 프론트로 보내주도록
    @GetMapping("/detail/{classId}")
    public ResponseEntity<ClassDetail> getClassDetail(@PathVariable("classId") int classId) {
        ClassDetail classDetail = classService.getClassDetail(classId);
        System.out.println("📌 백엔드 응답 데이터: " + classDetail); // ✅ 서버에서 데이터 로그 출력
        if (classDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(classDetail);
    }
}
