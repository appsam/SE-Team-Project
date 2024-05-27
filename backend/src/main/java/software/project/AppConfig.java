package software.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.project.service.GetMoviePoster;

@Configuration
public class AppConfig {

    @Bean
    public GetMoviePoster getMoviePoster() {
        return new GetMoviePoster();
    }
}