package com.api_stock.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiStockApplication.class, args);
	}

}
