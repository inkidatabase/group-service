package inkidatabase.groupservice;

import inkidatabase.groupservice.controller.GroupController;
import inkidatabase.groupservice.repository.GroupRepository;
import inkidatabase.groupservice.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class GroupServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Application context should not be null");
        
        // Verify essential beans are loaded
        assertTrue(applicationContext.containsBean("groupController"), "GroupController bean should be present");
        assertTrue(applicationContext.containsBean("groupServiceImpl"), "GroupService bean should be present");
        assertTrue(applicationContext.containsBean("groupRepository"), "GroupRepository bean should be present");
        
        // Verify we can get instances of our components
        assertNotNull(applicationContext.getBean(GroupController.class), "Should be able to get GroupController instance");
        assertNotNull(applicationContext.getBean(GroupService.class), "Should be able to get GroupService instance");
        assertNotNull(applicationContext.getBean(GroupRepository.class), "Should be able to get GroupRepository instance");
    }

    @Test
    void mainMethodStartsApplication() {
        // Set test properties
        System.setProperty("spring.profiles.active", "test");
        System.setProperty("spring.datasource.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "");
        System.setProperty("spring.datasource.driver-class-name", "org.h2.Driver");
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
        
        // Run the application
        assertDoesNotThrow(() -> {
            GroupServiceApplication.main(new String[] {});
        }, "Application should start without throwing exceptions");
        
        // Verify the application is running with test profile
        assertEquals("test", System.getProperty("spring.profiles.active"), 
            "Application should be running with test profile");
    }
}
