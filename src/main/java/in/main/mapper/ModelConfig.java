package in.main.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

	@Bean
	public ModelMapper model() {
		return new ModelMapper();
	}
}
