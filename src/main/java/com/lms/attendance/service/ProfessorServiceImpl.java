//package com.lms.attendance.service;
//
//import com.lms.attendance.model.Professor;
//import com.lms.attendance.repository.ProfessorMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ProfessorServiceImpl implements ProfessorService {
//
//    private final ProfessorMapper professorMapper;
//
//    @Override
//    public List<Professor> getAllProfessors() {
//        return professorMapper.getAllProfessors();
//    }
//
//    @Override
//    public Professor getProfessorById(String prof_id) {
//        return professorMapper.getProfessorById(prof_id);
//    }
//
//    @Override
//    public void saveProfessor(Professor professor) {
//        // 교수 정보를 저장하기 전에 출력
//        System.out.println("Professor name: " + professor.getName());
//        System.out.println("Professor department: " + professor.getDepartment());
//        System.out.println("Professor email: " + professor.getEmail());
//        
//        professorMapper.insertProfessor(professor);
//    }
//
//    @Override
//    public void updateProfessor(Professor professor) {
//        professorMapper.updateProfessor(professor);
//    }
//
//    @Override
//    public void deleteProfessor(String prof_id) {
//        professorMapper.deleteProfessor(prof_id);
//    }
//}