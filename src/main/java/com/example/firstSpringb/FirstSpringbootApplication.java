package com.example.firstSpringb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.*.dao")
public class FirstSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringbootApplication.class, args);
	}

}
