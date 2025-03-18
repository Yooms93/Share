//package com.lms.attendance.controller;
//
//import com.lms.attendance.model.Professor;
//import com.lms.attendance.service.ProfessorService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/professors")
//@RequiredArgsConstructor
//public class ProfessorController {
//
//    private final ProfessorService professorService;
//
//    // ✅ 모든 교수 조회 (관리자만 가능)
//    @GetMapping
//    public ResponseEntity<?> getAllProfessors(@RequestHeader("Authorization") String token) {
//        if (!isAdmin(token)) {
//            return ResponseEntity.status(403).body(Map.of("success", false, "message", "관리자만 조회 가능합니다."));
//        }
//        List<Professor> professors = professorService.getAllProfessors();
//        return ResponseEntity.ok(professors);
//    }
//
//    // ✅ 교수 등록 (관리자만 가능)
//    @PostMapping("/add")  // 이 부분을 /add로 맞춰줍니다.
//    public ResponseEntity<?> registerProfessor(@RequestHeader("Authorization") String token, @RequestBody Professor professor) {
//        if (!isAdmin(token)) {
//            return ResponseEntity.status(403).body(Map.of("success", false, "message", "관리자만 등록할 수 있습니다."));
//        }
//        if (professor.getProf_id() == null || professor.getProf_id().isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "교수 ID는 필수 입력값입니다."));
//        }
//        professorService.saveProfessor(professor);
//        return ResponseEntity.ok(Map.of("success", true, "message", "교수 등록 완료"));
//    }
//
//    // ✅ 교수 정보 수정 (관리자만 가능)
//    @PutMapping("/{prof_id}")
//    public ResponseEntity<?> updateProfessor(@RequestHeader("Authorization") String token, 
//                                              @PathVariable("prof_id") String prof_id, 
//                                              @RequestBody Professor updatedProfessor) {
//        if (!isAdmin(token)) {
//            return ResponseEntity.status(403).body(Map.of("success", false, "message", "관리자만 수정할 수 있습니다."));
//        }
//
//        // 비밀번호가 있을 경우 처리
//        if (updatedProfessor.getPassword() != null && !updatedProfessor.getPassword().isEmpty()) {
//            // 비밀번호 암호화 처리 (필요시)
//            // 예: updatedProfessor.setPassword(encryptPassword(updatedProfessor.getPassword()));
//        }
//
//        professorService.updateProfessor(updatedProfessor);
//        return ResponseEntity.ok(Map.of("success", true, "message", "교수 정보 업데이트 성공"));
//    }
//
//    // ✅ 교수 삭제 (관리자만 가능)
//    @DeleteMapping("/{prof_id}")
//    public ResponseEntity<?> deleteProfessor(@RequestHeader("Authorization") String token, 
//                                              @PathVariable("prof_id") String prof_id) {
//        if (!isAdmin(token)) {
//            return ResponseEntity.status(403).body(Map.of("success", false, "message", "관리자만 삭제할 수 있습니다."));
//        }
//        professorService.deleteProfessor(prof_id);
//        return ResponseEntity.ok(Map.of("success", true, "message", "교수 삭제 완료"));
//    }
//    
//    // ✅ 특정 교수 정보 조회 API (GET 요청 허용)
//    @GetMapping("/{prof_id}")
//    public ResponseEntity<?> getProfessorById(@PathVariable("prof_id") String prof_id) {
//        Professor professor = professorService.getProfessorById(prof_id);
//        if (professor == null) {
//            return ResponseEntity.status(404).body(Map.of("success", false, "message", "교수 정보를 찾을 수 없습니다."));
//        }
//        return ResponseEntity.ok(professor);
//    }
//
//    // JWT 토큰에서 역할 추출하는 메서드 (임시 구현)
//    private boolean isAdmin(String token) {
//        // 실제 JWT 파싱 코드 구현 필요 (예: JWTDecoder 또는 JwtParser 사용)
//        String role = extractRoleFromToken(token);  // JWT에서 역할 정보 추출
//        return "admin".equalsIgnoreCase(role);
//    }
//
//    // JWT 토큰에서 역할(Role) 추출하는 메서드 (임시 구현)
//    private String extractRoleFromToken(String token) {
//        // 실제 JWT 토큰 파싱 로직을 여기에 구현해야 합니다.
//        // 예시로 "admin"을 리턴
//        return "admin"; // TODO: JWT에서 role을 추출하는 로직 구현
//    }
//}