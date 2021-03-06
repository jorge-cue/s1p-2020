package com.example.chaos.monkey.shopping.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

/**
 * @author Ryan Baxter
 */
@EnableConfigServer
@SpringBootApplication
public class ChaosConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaosConfigServerApplication.class, args);
	}

	@Bean
	public EnvironmentRepository environmentRepository() {
		return new CustomEnvironmentRepositry();
	}

}

class CustomEnvironmentRepositry implements EnvironmentRepository {

	Map<String, Object> chaos = new HashMap<>();
	PropertySource propertySource;

	public CustomEnvironmentRepositry() {
		this.chaos.put("chaos.monkey.enabled", true);
		propertySource = new PropertySource("toys-bestseller-chaos", this.chaos);
	}

	@Override
	public Environment findOne(String application, String profile, String label) {
		Environment env = new Environment(application, profile);
		if("toys-bestseller".equalsIgnoreCase(application)) {
			env.add(propertySource);
		}
		return env;
	}
}

