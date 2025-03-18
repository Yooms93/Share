package com.lms.attendance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.attendance.model.Student;
import com.lms.attendance.service.StudentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    // ✅ 수강생 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student newStudent) {
        studentService.registerStudent(newStudent);
        return ResponseEntity.ok("수강생 등록 완료");
    }

    // ✅ 특정 강의실의 모든 학생 조회
    @GetMapping("/class/{classId}")  //  `{}` 중괄호로 감싸서 명확하게 지정
    public ResponseEntity<List<Student>> getStudentsByClass(@PathVariable("classId") int classId) {
        List<Student> students = studentService.getStudentsByClass(classId);
        return ResponseEntity.ok(students);
    }
    
    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(
            @PathVariable("studentId") String studentId,
            @RequestBody Student updatedStudent) {
        studentService.updateStudent(studentId, updatedStudent);
        return ResponseEntity.ok("수강생 정보 업데이트 성공");
    }
    
    // 수강생 삭제
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") String studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok("수강생 삭제 완료");
    }

}
