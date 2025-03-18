package com.lms.attendance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lms.attendance.repository") //MyBatis 매퍼 패키지 지정
public class LearnAliveApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnAliveApplication.class, args);
	}

}
