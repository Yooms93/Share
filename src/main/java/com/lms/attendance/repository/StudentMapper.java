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

import com.lms.attendance.model.Student;

@Mapper
public interface StudentMapper {

    // ✅ 학생 등록 (INSERT)
    @Insert("""
        INSERT INTO Student (student_id, university, department, name, email, class_id, remarks)
        VALUES (#{studentId}, #{university}, #{department}, #{name}, #{email}, #{classId}, #{remarks})
    """)
    void registerStudent(Student student);

    // ✅ 특정 강의실에 속한 모든 학생 조회 (SELECT)
    @Select("""
    	    SELECT student_id, university, department, name, email, class_id, remarks
    	    FROM Student
    	    WHERE class_id = #{classId}
    	    ORDER BY 
    	        university ASC,   -- ✅ 단과대학 오름차순
    	        department ASC,    -- ✅ 학과 오름차순
    	        student_id ASC     -- ✅ 학번 오름차순
    	""")
    	@Results(id = "StudentResultMap", value = {
    	    @Result(column = "student_id", property = "studentId"),
    	    @Result(column = "university", property = "university"),
    	    @Result(column = "department", property = "department"),
    	    @Result(column = "name", property = "name"),
    	    @Result(column = "email", property = "email"),
    	    @Result(column = "class_id", property = "classId"),
    	    @Result(column = "remarks", property = "remarks") // ✅ 비고 컬럼 추가
    	})
    	List<Student> findStudentsByClass(@Param("classId") int classId);

    // ✅ 학생 정보 업데이트 (UPDATE)
    @Update("""
        UPDATE Student
        SET university = #{university}, department = #{department}, 
            name = #{name}, email = #{email}, class_id = #{classId}, remarks = #{remarks}
        WHERE student_id = #{studentId}
    """)
    void updateStudent(
        @Param("studentId") String studentId,
        @Param("university") String university,
        @Param("department") String department,
        @Param("name") String name,
        @Param("email") String email,
        @Param("classId") int classId,
        @Param("remarks") String remarks
    );

    // ✅ 학생 삭제 (DELETE)
    @Delete("""
        DELETE FROM Student WHERE student_id = #{studentId}
    """)
    void deleteStudent(@Param("studentId") String studentId);
}
