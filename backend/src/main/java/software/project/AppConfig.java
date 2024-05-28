package software.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import software.project.service.GetMoviePoster;

@Configuration
public class AppConfig {

    @Bean
    public GetMoviePoster getMoviePoster() {
        return new GetMoviePoster();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}