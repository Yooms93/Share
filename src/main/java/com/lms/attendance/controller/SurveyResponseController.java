package com.lms.attendance.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.attendance.model.StudentSurveyStatus;
import com.lms.attendance.model.SurveyResponse;
import com.lms.attendance.model.SurveyResponseVisualization;
import com.lms.attendance.service.SurveyResponseService;

@RestController
@RequestMapping("/api/surveys")
public class SurveyResponseController {
    private final SurveyResponseService surveyResponseService;

    public SurveyResponseController(SurveyResponseService surveyResponseService) {
        this.surveyResponseService = surveyResponseService;
    }

    /** ✅ 특정 사용자의 응답 조회 */
    @GetMapping("/{surveyId}/response/{userId}")
    public ResponseEntity<List<SurveyResponse>> getUserResponse(
    		 @PathVariable(value = "surveyId") int surveyId, @PathVariable(value = "userId") String userId) {
        List<SurveyResponse> responses = surveyResponseService.getUserResponse(surveyId, userId);
        if (responses.isEmpty()) {
        	 return ResponseEntity.ok(Collections.emptyList());  // ✅ 빈 배열 반환
        }
        return ResponseEntity.ok(responses);
    }

    /** ✅ 응답 제출 또는 수정 */
    @PostMapping("/{surveyId}/response/{userId}")
    public ResponseEntity<String> submitOrUpdateResponse(
            @PathVariable("surveyId") int surveyId, 
            @PathVariable("userId") String userId, 
            @RequestBody Map<Integer, String> responses) {

        boolean isUpdated = surveyResponseService.submitOrUpdateResponse(surveyId, userId, responses);
        return ResponseEntity.ok(isUpdated ? "응답이 수정되었습니다." : "응답이 제출되었습니다.");
    }
    
    /** ✅ 응답 제출 여부를 조회 */
    @GetMapping("/{surveyId}/response-status")
    public ResponseEntity<?> getSurveyResponseStatus(@PathVariable("surveyId") int surveyId) { // ✅ 명시적 지정
        List<StudentSurveyStatus> responseList = surveyResponseService.getSurveyResponseStatus(surveyId);
        
        if (responseList == null || responseList.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // ✅ 빈 배열 반환
        }

        return ResponseEntity.ok(responseList);
    }
    
    /**✅ 특정 설문조사의 모든 응답을 시각화 용도로 가져오는 API*/ 
    @GetMapping("/{surveyId}/responses/visualization")
    public ResponseEntity<?> getSurveyResponsesForVisualization(@PathVariable("surveyId") Integer surveyId) {
        if (surveyId == null) {
            return ResponseEntity.badRequest().body("Survey ID is required");
        }

        List<SurveyResponseVisualization> responses = surveyResponseService.getSurveyResponsesForVisualization(surveyId);

        if (responses.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // ✅ 응답 없으면 빈 배열 반환
        }

        return ResponseEntity.ok(responses);
    }
}
