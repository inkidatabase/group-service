package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GroupRepositoryTest {
    
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void testFindAll() {
        List<Group> groups = groupRepository.findAll();
        assertTrue(groups.isEmpty());
    }

    @Test
    void testFindAllMoreThanOneGroup() {
        // Create first group
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        groupRepository.save(group1);

        // Create second group
        Group group2 = new Group("Stray Kids", "JYP Entertainment", 2018);
        groupRepository.save(group2);

        // Test finding all groups
        List<Group> groups = groupRepository.findAll();
        assertEquals(2, groups.size());
        
        Group savedGroup1 = groups.stream()
                .filter(g -> g.getGroupName().equals("BTS"))
                .findFirst()
                .orElseThrow();
        assertNotNull(savedGroup1.getGroupId());
        assertEquals("BigHit Music", savedGroup1.getAgency());
        assertEquals(2013, savedGroup1.getDebutYear());
        
        Group savedGroup2 = groups.stream()
                .filter(g -> g.getGroupName().equals("Stray Kids"))
                .findFirst()
                .orElseThrow();
        assertNotNull(savedGroup2.getGroupId());
        assertEquals("JYP Entertainment", savedGroup2.getAgency());
        assertEquals(2018, savedGroup2.getDebutYear());
    }

    @Test
    void testFindById() {
        Group group = new Group("BTS", "BigHit Music", 2013);
        Group savedGroup = groupRepository.save(group);
        UUID groupId = savedGroup.getGroupId();

        Optional<Group> result = groupRepository.findById(groupId);
        assertTrue(result.isPresent());
        assertEquals(groupId, result.get().getGroupId());

        // Test non-existent ID
        Optional<Group> nonExistent = groupRepository.findById(UUID.randomUUID());
        assertTrue(nonExistent.isEmpty());
    }

    @Test
    void testFindByAgency() {
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        Group group2 = new Group("TXT", "BigHit Music", 2019);
        Group group3 = new Group("Stray Kids", "JYP Entertainment", 2018);
        
        groupRepository.save(group1);
        groupRepository.save(group2);
        groupRepository.save(group3);

        List<Group> bighitGroups = groupRepository.findByAgencyIgnoreCase("BigHit Music");
        assertEquals(2, bighitGroups.size());
        bighitGroups.forEach(group -> 
            assertEquals("BigHit Music", group.getAgency())
        );
    }

    @Test
    void testFindByDebutYear() {
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        Group group2 = new Group("TXT", "BigHit Music", 2019);
        
        groupRepository.save(group1);
        groupRepository.save(group2);

        List<Group> groups2013 = groupRepository.findByDebutYear(2013);
        assertEquals(1, groups2013.size());
        Group found = groups2013.get(0);
        assertEquals(2013, found.getDebutYear());
        assertEquals("BTS", found.getGroupName());
    }

    @Test
    void testFindActiveAndDisbandedGroups() {
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        Group group2 = new Group("2NE1", "YG Entertainment", 2009);
        group2.setDisbandYear(2016);
        
        groupRepository.save(group1);
        groupRepository.save(group2);

        List<Group> activeGroups = groupRepository.findActiveGroups();
        assertEquals(1, activeGroups.size());
        assertEquals("BTS", activeGroups.get(0).getGroupName());

        List<Group> disbandedGroups = groupRepository.findDisbandedGroups();
        assertEquals(1, disbandedGroups.size());
        assertEquals("2NE1", disbandedGroups.get(0).getGroupName());
    }

    @Test
    void testFindByMember() {
        // Test with current members
        Group group1 = new Group("BTS", "BigHit Music", 2013);
        group1.setMembers(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"));
        groupRepository.save(group1);

        // Test with former members
        Group group2 = new Group("2NE1", "YG Entertainment", 2009);
        group2.setMembers(Arrays.asList("CL", "Dara", "Bom"));
        group2.setFormerMembers(Arrays.asList("Minzy"));
        group2.setDisbandYear(2016);
        groupRepository.save(group2);

        // Test finding current member
        List<Group> currentMemberGroups = groupRepository.findByMember("Jimin");
        assertEquals(1, currentMemberGroups.size());
        Group foundCurrent = currentMemberGroups.get(0);
        assertTrue(foundCurrent.getMembers().contains("Jimin"));

        // Test finding former member
        List<Group> formerMemberGroups = groupRepository.findByMember("Minzy");
        assertEquals(1, formerMemberGroups.size());
        Group foundFormer = formerMemberGroups.get(0);
        assertTrue(foundFormer.getFormerMembers().contains("Minzy"));

        // Test with non-existent member
        List<Group> nonExistent = groupRepository.findByMember("NonExistentMember");
        assertTrue(nonExistent.isEmpty());
    }

    @Test
    void testFindByLabel() {
        Group group = new Group("BTS", "BigHit Music", 2013);
        group.setLabels(Arrays.asList("HYBE", "BigHit Music"));
        groupRepository.save(group);

        List<Group> groups = groupRepository.findByLabel("HYBE");
        assertEquals(1, groups.size());
        Group found = groups.get(0);
        assertTrue(found.getLabels().contains("HYBE"));

        // Test with non-existent label
        List<Group> nonExistent = groupRepository.findByLabel("NonExistentLabel");
        assertTrue(nonExistent.isEmpty());
    }
}
