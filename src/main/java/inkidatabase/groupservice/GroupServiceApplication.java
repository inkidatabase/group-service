package inkidatabase.groupservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "inkidatabase.groupservice.controller",
    "inkidatabase.groupservice.service",
    "inkidatabase.groupservice.repository"
})
@EntityScan("inkidatabase.groupservice.model")
@EnableJpaRepositories("inkidatabase.groupservice.repository")
public class GroupServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GroupServiceApplication.class);
        app.run(args);
    }
}
