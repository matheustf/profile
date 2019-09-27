package com.gym.fitpass.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gym.fitpass.profile.model.v1.MarketingClienteTO;

@SpringBootApplication
public class ProfileApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileApplication.class, args);
		
		MarketingClienteTO ma = new MarketingClienteTO();
	}

}
