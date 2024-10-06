package com.chessrating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ChessRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessRatingApplication.class, args);
	}

}
