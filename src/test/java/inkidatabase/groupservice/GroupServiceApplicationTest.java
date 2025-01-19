package inkidatabase.groupservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
    "spring.main.banner-mode=off",
    "spring.jpa.hibernate.ddl-auto=none"
})
class GroupServiceApplicationTests {

    @Test
    void contextLoads() {
        // Test passes if Spring context loads successfully
        GroupServiceApplication.main(new String[]{
            "--spring.main.web-application-type=none",
            "--spring.jpa.hibernate.ddl-auto=none"
        });
    }
}
