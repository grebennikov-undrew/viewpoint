package com.grebennikovas.viewpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ViewpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewpointApplication.class, args);
	}

}
