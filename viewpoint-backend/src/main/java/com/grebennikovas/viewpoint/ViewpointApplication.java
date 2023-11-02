package com.grebennikovas.viewpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class ViewpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewpointApplication.class, args);
	}


}
