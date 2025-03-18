package com.lms.attendance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.attendance.model.Survey;
import com.lms.attendance.model.SurveyBoard;
import com.lms.attendance.service.SurveyService;

@RestController
@RequestMapping("/api/surveys") // ✅ API 기본 경로
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /** ✅ 특정 강의실의 모든 설문조사 게시판 조회 */
    @GetMapping("/boards/{classId}")
    public ResponseEntity<List<SurveyBoard>> getSurveyBoards(@PathVariable("classId") int classId) {
        return ResponseEntity.ok(surveyService.getSurveyBoardsByClassId(classId));
    }

    /** ✅ 설문조사 게시판 생성 */
    @PostMapping("/board/{classId}")
    public ResponseEntity<SurveyBoard> createSurveyBoard(@PathVariable("classId") int classId) {
        SurveyBoard createdBoard = surveyService.createSurveyBoard(classId);
        if (createdBoard == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdBoard);
    }

    /** ✅ 특정 게시판의 설문조사 목록 조회 */
    @GetMapping("/board/{boardId}/surveys")
    public ResponseEntity<List<Survey>> getSurveysByBoard(@PathVariable("boardId") int boardId) {
        return ResponseEntity.ok(surveyService.getSurveysByBoard(boardId));
    }

    /** ✅ 설문조사 + 질문을 한 번에 저장 */
    @PostMapping("/create")
    public ResponseEntity<Survey> createSurveyWithQuestions(@RequestBody Survey survey) {
        if (survey == null || survey.getQuestions() == null || survey.getQuestions().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // ✅ 요청 데이터가 올바르지 않을 경우 400 반환
        }

        // ✅ 요청 데이터 로깅
        System.out.println("📌 요청된 설문조사 데이터: " + survey.toString());

        Survey createdSurvey = surveyService.createSurveyWithQuestions(survey);
        if (createdSurvey == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdSurvey);
    }
    
    /** ✅ 특정 설문조사 상세 조회 */
    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<Survey> getSurveyDetail(@PathVariable("surveyId") int surveyId) {
        Survey survey = surveyService.getSurveyDetail(surveyId);
        if (survey == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(survey);
    }
    
    /** 특정 설문조사의 응답 가능 시간 변경 */
    @PutMapping("/{surveyId}/update-times")
    public ResponseEntity<?> updateSurveyTimes(@PathVariable("surveyId") Long surveyId, 
                                               @RequestBody Map<String, String> request) {
        String newStartTime = request.get("startTime");
        String newEndTime = request.get("endTime");
        boolean success = surveyService.updateSurveyTimes(surveyId, newStartTime, newEndTime);

        return success ? ResponseEntity.ok("설문 시간이 업데이트되었습니다.") :
                         ResponseEntity.status(HttpStatus.BAD_REQUEST).body("업데이트 실패");
    }
}
