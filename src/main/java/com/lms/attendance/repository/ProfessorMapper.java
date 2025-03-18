//package com.lms.attendance.repository;
//
//import com.lms.attendance.model.Professor;
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface ProfessorMapper {
//
//    @Select("SELECT * FROM professor")
//    List<Professor> getAllProfessors();
//
//    @Select("SELECT * FROM professor WHERE prof_id = #{prof_id}")
//    Professor getProfessorById(@Param("prof_id") String prof_id);
//
//    @Insert("INSERT INTO professor (prof_id, name, department, email, password) VALUES (#{prof_id}, #{name}, #{department}, #{email}, #{password})")  // 비밀번호 추가
//    void insertProfessor(Professor professor);
//
//    @Update("UPDATE professor SET name = #{name}, department = #{department}, email = #{email}, password = #{password} WHERE prof_id = #{prof_id}")  // 비밀번호 추가
//    void updateProfessor(Professor professor);
//
//    @Delete("DELETE FROM professor WHERE prof_id = #{prof_id}")
//    void deleteProfessor(@Param("prof_id") String prof_id);
//}