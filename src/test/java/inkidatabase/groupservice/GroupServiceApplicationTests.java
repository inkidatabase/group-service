package inkidatabase.groupservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class GroupServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodStartsApplication() {
        System.setProperty("spring.profiles.active", "test");
        System.setProperty("spring.datasource.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "");
        System.setProperty("spring.datasource.driver-class-name", "org.h2.Driver");
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
        GroupServiceApplication.main(new String[] {});
    }
}
