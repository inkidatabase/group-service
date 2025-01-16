package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GroupRepositoryTest {
    
    private GroupRepository groupRepository;
    
    @BeforeEach
    public void setUp() {
        groupRepository = new GroupRepository();
    }

    @Test
    void testFindAll() {
        Iterator<Group> groupIterator = groupRepository.findAll();
        assertFalse(groupIterator.hasNext());
    }

    @Test
    void testFindAllMoreThanOneGroup() {
        // Create first group
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        groupRepository.create(group1);

        // Create second group
        Group group2 = new Group("Stray Kids", "JYP Entertainment", 2018);
        groupRepository.create(group2);

        // Test iteration and group properties
        Iterator<Group> groupIterator = groupRepository.findAll();
        
        assertTrue(groupIterator.hasNext());
        Group savedGroup1 = groupIterator.next();
        assertNotNull(savedGroup1.getGroupId());
        assertEquals("BTS", savedGroup1.getGroupName());
        assertEquals("BigHit Music", savedGroup1.getAgency());
        assertEquals(2013, savedGroup1.getDebutYear());
        
        assertTrue(groupIterator.hasNext());
        Group savedGroup2 = groupIterator.next();
        assertNotNull(savedGroup2.getGroupId());
        assertEquals("Stray Kids", savedGroup2.getGroupName());
        assertEquals("JYP Entertainment", savedGroup2.getAgency());
        assertEquals(2018, savedGroup2.getDebutYear());
        
        assertFalse(groupIterator.hasNext());
    }
}
