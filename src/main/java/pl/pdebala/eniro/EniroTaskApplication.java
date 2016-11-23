package pl.pdebala.eniro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.concurrent.Executor;

/**
 * When we want to run SpringBoot Application like war on Tomcat server we have to
 * extends class {@link SpringBootServletInitializer}, and overload method {@link SpringBootServletInitializer#configure(SpringApplicationBuilder)}
 * e.g in this way:
 * {@code :}
 *  <pre class="code">
 *   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
 *       return application.sources(EniroTaskApplication.class);
 *   }
 * </pre>
 *
 * Additionally you have to change packaging type form 'jar' to 'war' in pom.xml file,
 * and set tomcat starter dependency as a provided.
 *
 * If you want to run SpringBoot app on embedded tomcat, but different one than delivered with
 * springBoot starters, you can use tomcat7-maven-plugin in your pom.xml file.
 * Running such server is very simple, you need just use command: mvn tomcat7:run
 *
 */
@SpringBootApplication
@EnableAsync
public class EniroTaskApplication {

    public static void main(String[] args) {
        //SpringApplication.run(EniroTaskApplication.class, args);
        new SpringApplicationBuilder().sources(EniroTaskApplication.class).run(args);
    }

    @Bean
    AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }


}
