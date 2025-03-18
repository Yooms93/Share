package com.lms.attendance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.attendance.util.DatabaseUtil;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    private final DatabaseUtil databaseUtil; // MyBatis 사용 시 DatabaseService

    public DatabaseController(DatabaseUtil databaseUtil) {
        this.databaseUtil = databaseUtil;
    }

    @GetMapping("/processlist")
    public List<Map<String, Object>> getProcessList() {
        return databaseUtil.getProcessList();
    }

    @DeleteMapping("/kill/{id}")
    public String killConnection(@PathVariable("id") int id) {  // <-- "id" 명시적으로 지정
        databaseUtil.killConnection(id);
        return "Killed connection with ID: " + id;
    }
}