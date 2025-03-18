package com.lms.attendance.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseUtil {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseUtil(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getProcessList() {
        return jdbcTemplate.queryForList("SHOW PROCESSLIST;");
    }

    public void killConnection(int connectionId) {
        jdbcTemplate.update("KILL " + connectionId);
    }
}