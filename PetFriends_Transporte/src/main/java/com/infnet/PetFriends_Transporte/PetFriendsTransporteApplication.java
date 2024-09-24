package com.infnet.PetFriends_Transporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PetFriendsTransporteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetFriendsTransporteApplication.class, args);
	}

}
