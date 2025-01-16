package inkidatabase.groupservice;

import inkidatabase.groupservice.controller.GroupController;
import inkidatabase.groupservice.repository.GroupRepository;
import inkidatabase.groupservice.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext);
        
        // Verify all required beans are loaded
        assertTrue(applicationContext.containsBean("groupController"));
        assertTrue(applicationContext.containsBean("groupServiceImpl"));
        assertTrue(applicationContext.containsBean("groupRepository"));
        
        // Verify we can get instances of our components
        assertNotNull(applicationContext.getBean(GroupController.class));
        assertNotNull(applicationContext.getBean(GroupService.class));
        assertNotNull(applicationContext.getBean(GroupRepository.class));
    }

    @Test
    void mainMethodStartsApplication() {
        // Test the main method (for coverage)
        GroupServiceApplication.main(new String[]{});
        
        // If we get here without exception, the application started successfully
        assertTrue(true);
    }
}
