package com.lms.attendance.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lms.attendance.model.ClassDetail;
import com.lms.attendance.model.ClassSettings;
import com.lms.attendance.model.Classroom;

@Mapper
public interface ClassMapper {
    
	@Select("(SELECT c.class_id AS classId, c.class_name AS className, c.prof_id AS profId " +
	        "FROM Class c JOIN Student s ON c.class_id = s.class_id WHERE s.student_id = #{userId}) " +
	        "UNION " +
	        "(SELECT c.class_id AS classId, c.class_name AS className, c.prof_id AS profId " +
	        "FROM Class c WHERE c.prof_id = #{userId})")
	List<Classroom> findClassesByUserId(String userId);

	@Insert("""
	        INSERT INTO Class (class_name, prof_id)
	        VALUES (#{className}, #{profId})
	    """)
	    void insertClassroom(Classroom newClass);
	
	// ✅ 강의실 삭제 SQL
    @Delete("DELETE FROM Class WHERE class_id = #{classId}")
    void deleteClassById(int classId);
	
    //모든 강의실 가져오기
    @Select("SELECT class_id, class_name, prof_id FROM Class")
    @Results({
        @Result(column = "class_id", property = "classId"),
        @Result(column = "class_name", property = "className"),
        @Result(column = "prof_id", property = "profId")
    })
    List<Classroom> findAllClasses();
    
    //특정 강의실의 출석 시간 정보
    @Select("""
            SELECT start_time, end_time, present_start, present_end, late_end 
            FROM Class
            WHERE class_id = #{classId}
        """)
        @Results(id = "ClassSettingsResultMap", value = {
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "presentStart", column = "present_start"),
            @Result(property = "presentEnd", column = "present_end"),
            @Result(property = "lateEnd", column = "late_end")
        })
        ClassSettings findClassSettingsById(@Param("classId") int classId);
    

    // ✅ 출석 시간 업데이트
    @Update("""
        UPDATE Class
        SET start_time = #{startTime}, 
            end_time = #{endTime}, 
            present_start = #{presentStart}, 
            present_end = #{presentEnd}, 
            late_end = #{lateEnd}
        WHERE class_id = #{classId}
    """)
    void updateClassSettings(ClassSettings settings);

    
    // 클래스 정보를 가져오는 쿼리
    @Select("""
    	    SELECT 
    	        c.class_id, 
    	        c.class_name, 
    	        c.prof_id,  -- ✅ VARCHAR 컬럼이므로 String으로 처리해야 함
    	        p.name AS professor_name, 
    	        p.email AS professor_email 
    	    FROM Class c
    	    JOIN Professor p ON c.prof_id = p.prof_id
    	    WHERE c.class_id = #{classId}
    	""")
    	@Results({
    	    @Result(column = "class_id", property = "classId"),
    	    @Result(column = "class_name", property = "className"),
    	    @Result(column = "prof_id", property = "profId"), // ✅ String으로 변경
    	    @Result(column = "professor_name", property = "professorName"),
    	    @Result(column = "professor_email", property = "professorEmail")
    	})
    	ClassDetail findClassDetailById(int classId);
    
    @Select("SELECT present_start, present_end, late_end FROM Class WHERE class_id = #{classId}")
	ClassSettings getClassSettings(@Param("classId") int classId);
	}

