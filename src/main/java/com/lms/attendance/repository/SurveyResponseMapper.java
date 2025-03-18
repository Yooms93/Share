package com.lms.attendance.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lms.attendance.model.StudentSurveyStatus;
import com.lms.attendance.model.SurveyResponse;
import com.lms.attendance.model.SurveyResponseVisualization;

@Mapper
public interface SurveyResponseMapper {

    /** ✅ 특정 사용자의 기존 응답 조회 */
    @Select("SELECT * FROM survey_response WHERE survey_id = #{surveyId} AND user_id = #{userId}")
    @Results({
        @Result(property = "surveyId", column = "survey_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "questionId", column = "question_id"),
        @Result(property = "response", column = "response")
    })
    List<SurveyResponse> getUserResponse(@Param("surveyId") int surveyId, @Param("userId") String userId);

    /** ✅ 특정 질문의 기존 응답 조회 */
    @Select("SELECT * FROM survey_response WHERE survey_id = #{surveyId} AND user_id = #{userId} AND question_id = #{questionId}")
    SurveyResponse findResponse(@Param("surveyId") int surveyId, @Param("userId") String userId, @Param("questionId") int questionId);

    /** ✅ 새 응답 저장 */
    @Insert("INSERT INTO survey_response (survey_id, user_id, question_id, response) VALUES (#{surveyId}, #{userId}, #{questionId}, #{response})")
    void insertResponse(@Param("surveyId") int surveyId, @Param("userId") String userId, @Param("questionId") int questionId, @Param("response") String response);

    /** ✅ 기존 응답 수정 */
    @Update("UPDATE survey_response SET response = #{response} WHERE survey_id = #{surveyId} AND user_id = #{userId} AND question_id = #{questionId}")
    void updateResponse(@Param("surveyId") int surveyId, @Param("userId") String userId, @Param("questionId") int questionId, @Param("response") String response);

    /** 응답 여부 조회*/
    @Select("""
            SELECT 
                s.university AS university,
                s.department AS department,
                s.student_id AS studentId,
                s.name AS name,
                s.remarks AS remarks,
                CASE 
                    WHEN sr.user_id IS NOT NULL THEN '응답 완료'
                    ELSE '미응답'
                END AS responseStatus
            FROM Student s
            LEFT JOIN (
                SELECT DISTINCT user_id
                FROM survey_response
                WHERE survey_id = #{surveyId}
            ) sr ON s.student_id = sr.user_id
            ORDER BY s.university ASC, s.department ASC, s.student_id ASC
    """)
    List<StudentSurveyStatus> getSurveyResponseStatus(@Param("surveyId") int surveyId); // ✅ @Param 명시

    /** ✅ 특정 설문조사의 모든 응답을 시각화용으로 가져오기 */
    @Select("""
            SELECT 
                q.question_id AS questionId,
                q.survey_id AS surveyId,
                q.question_text AS questionText,
                q.question_type AS questionType,
                sr.response AS response,
                s.student_id AS studentId,
                s.name AS name
            FROM survey_response sr
            JOIN survey_question q ON sr.question_id = q.question_id
            LEFT JOIN Student s ON sr.user_id = s.student_id
            WHERE q.survey_id = #{surveyId}
            ORDER BY q.question_id ASC, s.student_id ASC
            """)
    List<SurveyResponseVisualization> getSurveyResponsesForVisualization(@Param("surveyId") Integer surveyId);
}
