package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mapper")//マッパーインターフェースがあるパッケージを指定
public class AAsamApplication {

	public static void main(String[] args) {
		SpringApplication.run(AAsamApplication.class, args);
	}

}
