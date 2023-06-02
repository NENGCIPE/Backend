package Nengcipe.NengcipeBackend;

import Nengcipe.NengcipeBackend.domain.Recipe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class NengcipeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NengcipeBackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://nengcipe.com");
			}
		};
	}


}
