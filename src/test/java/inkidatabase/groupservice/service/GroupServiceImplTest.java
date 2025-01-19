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
import static org.mockito.Mockito.*;
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
        // Arrange
        Group groupWithNullId = new Group("TXT", "HYBE", 2019);
        groupWithNullId.setGroupId(null);
        when(groupRepository.save(any(Group.class))).thenAnswer(i -> i.getArgument(0));
        
        // Act
        Group createdGroup = groupService.create(groupWithNullId);
        
        // Assert
        assertNotNull(createdGroup.getGroupId(), "Group ID should be generated when null");
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void create_WithExistingId_ShouldPreserveId() {
        // Arrange
        when(groupRepository.save(testGroup)).thenReturn(testGroup);
        
        // Act
        Group createdGroup = groupService.create(testGroup);
        
        // Assert
        assertEquals(testId, createdGroup.getGroupId(), "Group ID should be preserved when provided");
        verify(groupRepository).save(testGroup);
    }

    @Test
    void findAll_ShouldReturnAllGroups() {
        // Arrange
        List<Group> expectedGroups = Arrays.asList(
            testGroup,
            new Group("TXT", "HYBE", 2019)
        );
        when(groupRepository.findAll()).thenReturn(expectedGroups);

        // Act
        List<Group> actualGroups = groupService.findAll();
        
        // Assert
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain test group");
        verify(groupRepository).findAll();
    }

    @Test
    void findById_WhenExists_ShouldReturnGroup() {
        // Arrange
        when(groupRepository.findById(testId)).thenReturn(Optional.of(testGroup));

        // Act
        Optional<Group> found = groupService.findById(testId);
        
        // Assert
        assertTrue(found.isPresent(), "Should find existing group");
        assertEquals(testGroup, found.get(), "Should return correct group");
        verify(groupRepository).findById(testId);
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(groupRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<Group> found = groupService.findById(testId);
        
        // Assert
        assertTrue(found.isEmpty(), "Should return empty when group not found");
        verify(groupRepository).findById(testId);
    }

    @Test
    void findByAgency_ShouldReturnMatchingGroups() {
        // Arrange
        List<Group> expectedGroups = Collections.singletonList(testGroup);
        when(groupRepository.findByAgencyIgnoreCase("HYBE")).thenReturn(expectedGroups);

        // Act
        List<Group> actualGroups = groupService.findByAgency("HYBE");
        
        // Assert
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all matching groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain matching group");
        verify(groupRepository).findByAgencyIgnoreCase("HYBE");
    }

    @Test
    void findByDebutYear_ShouldReturnMatchingGroups() {
        // Arrange
        List<Group> expectedGroups = Collections.singletonList(testGroup);
        when(groupRepository.findByDebutYear(2013)).thenReturn(expectedGroups);

        // Act
        List<Group> actualGroups = groupService.findByDebutYear(2013);
        
        // Assert
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all matching groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain matching group");
        verify(groupRepository).findByDebutYear(2013);
    }

    @Test
    void findActiveGroups_ShouldReturnActiveGroups() {
        // Arrange
        List<Group> expectedGroups = Collections.singletonList(testGroup);
        when(groupRepository.findActiveGroups()).thenReturn(expectedGroups);

        // Act
        List<Group> actualGroups = groupService.findActiveGroups();
        
        // Assert
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all active groups");
        assertTrue(actualGroups.contains(testGroup), "Should contain active group");
        verify(groupRepository).findActiveGroups();
    }

    @Test
    void findDisbandedGroups_ShouldReturnDisbandedGroups() {
        // Arrange
        Group disbandedGroup = new Group("2NE1", "YG", 2009);
        disbandedGroup.setDisbandYear(2016);
        List<Group> expectedGroups = Collections.singletonList(disbandedGroup);
        when(groupRepository.findDisbandedGroups()).thenReturn(expectedGroups);

        // Act
        List<Group> actualGroups = groupService.findDisbandedGroups();
        
        // Assert
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all disbanded groups");
        assertTrue(actualGroups.contains(disbandedGroup), "Should contain disbanded group");
        verify(groupRepository).findDisbandedGroups();
    }

    @Test
    void findByMember_ShouldReturnGroupsWithMember() {
        // Arrange
        List<Group> expectedGroups = Collections.singletonList(testGroup);
        when(groupRepository.findByMember("RM")).thenReturn(expectedGroups);

        // Act
        List<Group> actualGroups = groupService.findByMember("RM");
        
        // Assert
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all groups with member");
        assertTrue(actualGroups.contains(testGroup), "Should contain group with member");
        verify(groupRepository).findByMember("RM");
    }

    @Test
    void findByLabel_ShouldReturnGroupsWithLabel() {
        // Arrange
        List<Group> expectedGroups = Collections.singletonList(testGroup);
        when(groupRepository.findByLabel("kpop")).thenReturn(expectedGroups);

        // Act
        List<Group> actualGroups = groupService.findByLabel("kpop");
        
        // Assert
        assertEquals(expectedGroups.size(), actualGroups.size(), "Should return all groups with label");
        assertTrue(actualGroups.contains(testGroup), "Should contain group with label");
        verify(groupRepository).findByLabel("kpop");
    }
}
