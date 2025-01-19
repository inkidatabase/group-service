package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GroupRepositoryTest {
    
    private GroupRepository groupRepository;
    
    @BeforeEach
    public void setUp() {
        groupRepository = new GroupRepository();
    }

    @AfterEach
    public void tearDown() {
        // Clear all groups after each test
        Iterator<Group> iterator = groupRepository.findAll();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
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

    @Test
    void testFindById() {
        Group group = new Group("BTS", "BigHit Music", 2013);
        groupRepository.create(group);
        UUID groupId = group.getGroupId();

        Iterator<Group> result = groupRepository.findById(groupId);
        assertTrue(result.hasNext());
        assertEquals(groupId, result.next().getGroupId());
        assertFalse(result.hasNext());

        // Test non-existent ID
        Iterator<Group> nonExistent = groupRepository.findById(UUID.randomUUID());
        assertFalse(nonExistent.hasNext());
    }

    @Test
    void testFindByAgency() {
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        Group group2 = new Group("TXT", "BigHit Music", 2019);
        Group group3 = new Group("Stray Kids", "JYP Entertainment", 2018);
        
        groupRepository.create(group1);
        groupRepository.create(group2);
        groupRepository.create(group3);

        Iterator<Group> bighitGroups = groupRepository.findByAgency("BigHit Music");
        int count = 0;
        while (bighitGroups.hasNext()) {
            assertEquals("BigHit Music", bighitGroups.next().getAgency());
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testFindByDebutYear() {
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        Group group2 = new Group("TXT", "BigHit Music", 2019);
        
        groupRepository.create(group1);
        groupRepository.create(group2);

        Iterator<Group> groups2013 = groupRepository.findByDebutYear(2013);
        assertTrue(groups2013.hasNext());
        Group found = groups2013.next();
        assertEquals(2013, found.getDebutYear());
        assertEquals("BTS", found.getGroupName());
        assertFalse(groups2013.hasNext());
    }

    @Test
    void testFindActiveAndDisbandedGroups() {
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        Group group2 = new Group("2NE1", "YG Entertainment", 2009);
        group2.setDisbandYear(2016);
        
        groupRepository.create(group1);
        groupRepository.create(group2);

        Iterator<Group> activeGroups = groupRepository.findActiveGroups();
        assertTrue(activeGroups.hasNext());
        assertEquals("BTS", activeGroups.next().getGroupName());
        assertFalse(activeGroups.hasNext());

        Iterator<Group> disbandedGroups = groupRepository.findDisbandedGroups();
        assertTrue(disbandedGroups.hasNext());
        assertEquals("2NE1", disbandedGroups.next().getGroupName());
        assertFalse(disbandedGroups.hasNext());
    }

    @Test
    void testFindByMember() {
        // Test with current members
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        group1.setMembers(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"));
        groupRepository.create(group1);

        // Test with former members
        Group group2 = new Group("2NE1", "YG Entertainment", 2009);
        group2.setMembers(Arrays.asList("CL", "Dara", "Bom"));
        group2.setFormerMembers(Arrays.asList("Minzy"));
        group2.setDisbandYear(2016);
        groupRepository.create(group2);

        // Test finding current member
        Iterator<Group> currentMemberGroups = groupRepository.findByMember("Jimin");
        assertTrue(currentMemberGroups.hasNext());
        Group foundCurrent = currentMemberGroups.next();
        assertTrue(foundCurrent.getMembers().contains("Jimin"));
        assertFalse(currentMemberGroups.hasNext());

        // Test finding former member
        Iterator<Group> formerMemberGroups = groupRepository.findByMember("Minzy");
        assertTrue(formerMemberGroups.hasNext());
        Group foundFormer = formerMemberGroups.next();
        assertTrue(foundFormer.getFormerMembers().contains("Minzy"));
        assertFalse(formerMemberGroups.hasNext());

        // Test with non-existent member
        Iterator<Group> nonExistent = groupRepository.findByMember("NonExistentMember");
        assertFalse(nonExistent.hasNext());
    }

    @Test
    void testFindByLabel() {
        Group group = new Group("BTS", "BigHit Music", 2013);
        group.setLabels(Arrays.asList("HYBE", "BigHit Music"));
        groupRepository.create(group);

        Iterator<Group> groups = groupRepository.findByLabel("HYBE");
        assertTrue(groups.hasNext());
        Group found = groups.next();
        assertTrue(found.getLabels().contains("HYBE"));
        assertFalse(groups.hasNext());

        // Test with non-existent label
        Iterator<Group> nonExistent = groupRepository.findByLabel("NonExistentLabel");
        assertFalse(nonExistent.hasNext());
    }
}
