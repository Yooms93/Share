package com.lms.attendance.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SurveyResponse {
    private int surveyId;
    private String userId;
    private int questionId;
    private String response;

    public SurveyResponse(int surveyId, String userId, int questionId, String response) {
        this.surveyId = surveyId;
        this.userId = userId;
        this.questionId = questionId;
        this.response = response;
    }
}
