package com.lms.attendance.repository;

import org.apache.ibatis.annotations.*;

import com.lms.attendance.model.*;

import java.util.List;

@Mapper
public interface SurveyMapper {

    /** ✅ 특정 강의실의 모든 설문조사 게시판 목록 조회 */
    @Select("SELECT board_id AS boardId, class_id AS classId FROM survey_board WHERE class_id = #{classId} ORDER BY board_id ASC")
    List<SurveyBoard> findSurveyBoardsByClassId(@Param("classId") int classId);

    /** ✅ 설문조사 게시판 생성 */
    @Insert("INSERT INTO survey_board (class_id) VALUES (#{classId})")
    void createSurveyBoard(@Param("classId") int classId);

    /** ✅ 특정 강의실의 단일 설문조사 게시판 조회 */
    @Select("SELECT board_id AS boardId, class_id AS classId FROM survey_board WHERE class_id = #{classId} LIMIT 1")
    SurveyBoard getSurveyBoardByClassId(@Param("classId") int classId);

    /** ✅ 특정 게시판의 설문조사 목록 조회 */
    @Select("SELECT * FROM survey_post WHERE board_id = #{boardId}")
    @Results({
        @Result(property = "surveyId", column = "survey_id"),
        @Result(property = "boardId", column = "board_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<Survey> getSurveysByBoard(@Param("boardId") int boardId);

    /** ✅ 설문조사 생성 */
    @Insert("INSERT INTO survey_post (board_id, title, start_time, end_time) VALUES (#{boardId}, #{title}, #{startTime}, #{endTime})")
    @Options(useGeneratedKeys = true, keyProperty = "surveyId")
    void insertSurvey(Survey survey);

    /** ✅ 설문 질문 생성 */
    @Insert("""
    	    INSERT INTO survey_question 
    	    (survey_id, question_text, question_type, options, min_select, max_select, min_value, max_value, min_label, max_label, is_required) 
    	    VALUES 
    	    (#{surveyId}, #{questionText}, #{questionType}, 
    	        CASE 
    	            WHEN #{questionType} IN ('radio', 'checkbox') THEN CAST(#{options} AS JSON) 
    	            ELSE NULL 
    	        END, 
    	        CASE 
    	            WHEN #{questionType} = 'checkbox' THEN #{minSelect} 
    	            ELSE NULL 
    	        END,
    	        CASE 
    	            WHEN #{questionType} = 'checkbox' THEN #{maxSelect} 
    	            ELSE NULL 
    	        END,
    	        CASE 
    	            WHEN #{questionType} = 'linear_scale' THEN #{minValue} 
    	            ELSE NULL 
    	        END,
    	        CASE 
    	            WHEN #{questionType} = 'linear_scale' THEN #{maxValue} 
    	            ELSE NULL 
    	        END,
    	        CASE 
    	            WHEN #{questionType} = 'linear_scale' THEN #{minLabel} 
    	            ELSE NULL 
    	        END,
    	        CASE 
    	            WHEN #{questionType} = 'linear_scale' THEN #{maxLabel} 
    	            ELSE NULL 
    	        END,
    	        #{isRequired}
    	    )
    	""")
    	void insertSurveyQuestion(
    	    @Param("surveyId") int surveyId, 
    	    @Param("questionText") String questionText, 
    	    @Param("questionType") String questionType, 
    	    @Param("options") String options,
    	    @Param("minSelect") Integer minSelect,
    	    @Param("maxSelect") Integer maxSelect,
    	    @Param("minValue") Integer minValue,  // 기본형 int → Integer 변경
    	    @Param("maxValue") Integer maxValue,  // 기본형 int → Integer 변경
    	    @Param("minLabel") String minLabel,
    	    @Param("maxLabel") String maxLabel,
    	    @Param("isRequired") Boolean isRequired // 기본형 boolean → Boolean 변경
    	);


    /** ✅ 특정 설문조사 조회 */
    @Select("SELECT * FROM survey_post WHERE survey_id = #{surveyId}")
    @Results(id = "SurveyResult", value = {
        @Result(property = "surveyId", column = "survey_id"),
        @Result(property = "boardId", column = "board_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    Survey getSurveyById(@Param("surveyId") int surveyId);

    /** ✅ 해당 설문의 질문 목록 조회 */
    @Select("SELECT * FROM survey_question WHERE survey_id = #{surveyId}")
    @Results(id = "SurveyQuestionResult", value = {
        @Result(property = "questionId", column = "question_id"),
        @Result(property = "surveyId", column = "survey_id"),
        @Result(property = "questionText", column = "question_text"),
        @Result(property = "questionType", column = "question_type"),
        @Result(property = "options", column = "options"),
        @Result(property = "minSelect", column = "min_select"),
        @Result(property = "maxSelect", column = "max_select"),
        @Result(property = "minValue", column = "min_value"),
        @Result(property = "maxValue", column = "max_value"),
        @Result(property = "isRequired", column = "is_required"),
        @Result(property = "minLabel", column = "min_label"),
        @Result(property = "maxLabel", column = "max_label"),
        @Result(property = "createdAt", column = "created_at")
    })
    List<SurveyQuestion> getQuestionsBySurveyId(@Param("surveyId") int surveyId);

    @Update("UPDATE survey_post SET start_time = #{startTime}, end_time = #{endTime} WHERE survey_id = #{surveyId}")
    int updateSurveyTimes(@Param("surveyId") Long surveyId, 
                          @Param("startTime") String startTime, 
                          @Param("endTime") String endTime);
}
