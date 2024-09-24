package com.infnet.PetFriends_Almoxarifado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PetFriendsAlmoxarifadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetFriendsAlmoxarifadoApplication.class, args);
	}

}
