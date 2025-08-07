package com.ironia.ironfood.courier.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CourierManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierManagerApplication.class, args);
	}

}
