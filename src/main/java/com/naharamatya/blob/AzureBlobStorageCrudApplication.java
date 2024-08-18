package com.naharamatya.blob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AzureBlobStorageCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzureBlobStorageCrudApplication.class, args);
	}

}
