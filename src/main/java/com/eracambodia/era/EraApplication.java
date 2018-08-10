package com.eracambodia.era;

import com.eracambodia.era.configuration.fileupload.FileStorageProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperty.class})
public class EraApplication {

	public static void main(String[] args) {
		SpringApplication.run(EraApplication.class, args);
	}
}
