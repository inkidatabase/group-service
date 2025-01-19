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
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    private Group testGroup;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testGroup = new Group("BTS", "HYBE", 2013);
        testGroup.setGroupId(testId);
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"));
        testGroup.setLabels(Arrays.asList("kpop", "boy-group"));
    }

    @Test
    void create_WithNullId_ShouldGenerateNewId() {
        Group groupWithNullId = new Group("TXT", "HYBE", 2019);
        groupWithNullId.setGroupId(null);
        
        when(groupRepository.create(any(Group.class))).thenAnswer(i -> i.getArgument(0));
        
        Group createdGroup = groupService.create(groupWithNullId);
        assertNotNull(createdGroup.getGroupId(), "Group ID should be generated when null");
    }

    @Test
    void create_WithExistingId_ShouldPreserveId() {
        when(groupRepository.create(testGroup)).thenReturn(testGroup);
        
        Group createdGroup = groupService.create(testGroup);
        assertEquals(testId, createdGroup.getGroupId(), "Group ID should be preserved when provided");
    }

    @Test
    void findAll_ShouldReturnAllGroups() {
        List<Group> expectedGroups = Arrays.asList(
            testGroup,
            new Group("TXT", "HYBE", 2019)
        );
        when(groupRepository.findAll()).thenReturn(expectedGroups.iterator());

        List<Group> actualGroups = groupService.findAll();
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain test group");
    }

    @Test
    void findById_WhenExists_ShouldReturnGroup() {
        when(groupRepository.findById(testId)).thenReturn(Collections.singletonList(testGroup).iterator());

        Optional<Group> found = groupService.findById(testId);
        assertTrue(found.isPresent(), "Should find existing group");
        assertEquals(testGroup, found.get(), "Should return correct group");
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        when(groupRepository.findById(testId)).thenReturn(Collections.emptyIterator());

        Optional<Group> found = groupService.findById(testId);
        assertFalse(found.isPresent(), "Should return empty when group not found");
    }

    @Test
    void findByAgency_ShouldReturnMatchingGroups() {
        List<Group> expectedGroups = Arrays.asList(testGroup);
        when(groupRepository.findByAgency("HYBE")).thenReturn(expectedGroups.iterator());

        List<Group> actualGroups = groupService.findByAgency("HYBE");
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all matching groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain matching group");
    }

    @Test
    void findByDebutYear_ShouldReturnMatchingGroups() {
        List<Group> expectedGroups = Arrays.asList(testGroup);
        when(groupRepository.findByDebutYear(2013)).thenReturn(expectedGroups.iterator());

        List<Group> actualGroups = groupService.findByDebutYear(2013);
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all matching groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain matching group");
    }

    @Test
    void findActiveGroups_ShouldReturnActiveGroups() {
        List<Group> expectedGroups = Arrays.asList(testGroup);
        when(groupRepository.findActiveGroups()).thenReturn(expectedGroups.iterator());

        List<Group> actualGroups = groupService.findActiveGroups();
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all active groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain active group");
    }

    @Test
    void findDisbandedGroups_ShouldReturnDisbandedGroups() {
        Group disbandedGroup = new Group("2NE1", "YG", 2009);
        disbandedGroup.setDisbandYear(2016);
        List<Group> expectedGroups = Arrays.asList(disbandedGroup);
        when(groupRepository.findDisbandedGroups()).thenReturn(expectedGroups.iterator());

        List<Group> actualGroups = groupService.findDisbandedGroups();
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all disbanded groups");
        assertTrue(actualGroups.contains(disbandedGroup), "Should contain disbanded group");
    }

    @Test
    void findByMember_ShouldReturnGroupsWithMember() {
        List<Group> expectedGroups = Arrays.asList(testGroup);
        when(groupRepository.findByMember("RM")).thenReturn(expectedGroups.iterator());

        List<Group> actualGroups = groupService.findByMember("RM");
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all groups with member");
        assertTrue(actualGroups.contains(testGroup), "Should contain group with member");
    }

    @Test
    void findByLabel_ShouldReturnGroupsWithLabel() {
        List<Group> expectedGroups = Arrays.asList(testGroup);
        when(groupRepository.findByLabel("kpop")).thenReturn(expectedGroups.iterator());

        List<Group> actualGroups = groupService.findByLabel("kpop");
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all groups with label");
        assertTrue(actualGroups.contains(testGroup), "Should contain group with label");
    }
}
