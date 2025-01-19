package inkidatabase.groupservice.service;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    private Group testGroup;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testGroup = new Group("BTS", "BigHit Music", 2013);
        testId = testGroup.getGroupId();
    }

    @Test
    void testCreate() {
        when(groupRepository.save(any(Group.class))).thenReturn(testGroup);
        
        Group result = groupService.create(testGroup);
        
        assertNotNull(result);
        assertEquals(testGroup.getGroupName(), result.getGroupName());
        assertEquals(testGroup.getAgency(), result.getAgency());
        assertEquals(testGroup.getDebutYear(), result.getDebutYear());
    }

    @Test
    void testFindAll() {
        List<Group> groups = Arrays.asList(
            testGroup,
            new Group("TXT", "BigHit Music", 2019)
        );
        
        when(groupRepository.findAll()).thenReturn(groups);

        List<Group> result = groupService.findAll();
        
        assertEquals(2, result.size());
        assertEquals("BTS", result.get(0).getGroupName());
        assertEquals("TXT", result.get(1).getGroupName());
    }

    @Test
    void testFindById() {
        when(groupRepository.findById(testId)).thenReturn(Optional.of(testGroup));

        Optional<Group> result = groupService.findById(testId);
        
        assertTrue(result.isPresent());
        assertEquals(testGroup.getGroupName(), result.get().getGroupName());
    }

    @Test
    void testFindByIdNotFound() {
        when(groupRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<Group> result = groupService.findById(UUID.randomUUID());
        
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAgency() {
        List<Group> groups = Arrays.asList(
            testGroup,
            new Group("TXT", "BigHit Music", 2019)
        );
        
        when(groupRepository.findByAgencyIgnoreCase("BigHit Music")).thenReturn(groups);

        List<Group> result = groupService.findByAgency("BigHit Music");
        
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(g -> g.getAgency().equals("BigHit Music")));
    }

    @Test
    void testFindByDebutYear() {
        when(groupRepository.findByDebutYear(2013))
            .thenReturn(Collections.singletonList(testGroup));

        List<Group> result = groupService.findByDebutYear(2013);
        
        assertEquals(1, result.size());
        assertEquals(2013, result.get(0).getDebutYear());
    }

    @Test
    void testFindActiveGroups() {
        when(groupRepository.findActiveGroups())
            .thenReturn(Collections.singletonList(testGroup));

        List<Group> result = groupService.findActiveGroups();
        
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getDisbandYear());
    }

    @Test
    void testFindDisbandedGroups() {
        Group disbandedGroup = new Group("2NE1", "YG Entertainment", 2009);
        disbandedGroup.setDisbandYear(2016);
        
        when(groupRepository.findDisbandedGroups())
            .thenReturn(Collections.singletonList(disbandedGroup));

        List<Group> result = groupService.findDisbandedGroups();
        
        assertEquals(1, result.size());
        assertTrue(result.get(0).getDisbandYear() > 0);
    }

    @Test
    void testFindByMember() {
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga"));
        
        when(groupRepository.findByMember("RM"))
            .thenReturn(Collections.singletonList(testGroup));

        List<Group> result = groupService.findByMember("RM");
        
        assertEquals(1, result.size());
        assertTrue(result.get(0).getMembers().contains("RM"));
    }

    @Test
    void testFindByLabel() {
        testGroup.setLabels(Arrays.asList("HYBE", "BigHit Music"));
        
        when(groupRepository.findByLabel("HYBE"))
            .thenReturn(Collections.singletonList(testGroup));

        List<Group> result = groupService.findByLabel("HYBE");
        
        assertEquals(1, result.size());
        assertTrue(result.get(0).getLabels().contains("HYBE"));
    }
}
