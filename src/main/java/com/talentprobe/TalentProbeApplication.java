package com.talentprobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@PropertySources({
		@PropertySource("classpath:/spring-boot.properties")
})
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class TalentProbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentProbeApplication.class, args);
	}

}
