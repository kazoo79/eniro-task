package pl.pdebala.eniro;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.AsyncRestTemplate;

@SpringBootApplication
@EnableAsync
public class EniroTaskApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EniroTaskApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(EniroTaskApplication.class, args);
    }

    @Bean
    AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }

}
