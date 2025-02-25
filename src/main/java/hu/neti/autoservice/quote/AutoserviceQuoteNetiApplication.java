package hu.neti.autoservice.quote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class AutoserviceQuoteNetiApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AutoserviceQuoteNetiApplication.class, args);
        Environment env = context.getEnvironment();
        log.debug("***************** The {} application started on {} port *****************", env.getProperty("spring.application.name"), env.getProperty("server.port"));
    }
}
