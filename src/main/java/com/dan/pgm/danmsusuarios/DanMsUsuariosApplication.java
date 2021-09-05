package com.dan.pgm.danmsusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DanMsUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanMsUsuariosApplication.class, args);
	}

}
