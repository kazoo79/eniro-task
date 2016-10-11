package pl.pdebala.eniro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.AsyncRestTemplate;

@SpringBootApplication
@EnableAsync
public class EniroTaskApplication extends AsyncConfigurerSupport {

    public static void main(String[] args) {
        SpringApplication.run(EniroTaskApplication.class, args);
    }

    @Bean
    AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }

}
