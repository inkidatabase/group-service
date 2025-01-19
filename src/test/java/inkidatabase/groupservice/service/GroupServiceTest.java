package inkidatabase.groupservice.service;

import inkidatabase.groupservice.dto.CreateGroupRequest;
import inkidatabase.groupservice.dto.GroupDTO;
import inkidatabase.groupservice.dto.UpdateGroupRequest;
import inkidatabase.groupservice.mapper.GroupMapper;
import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository repository;

    @Mock
    private GroupMapper mapper;

    private GroupService groupService;
    private Group testGroup;
    private GroupDTO testGroupDTO;
    private CreateGroupRequest createRequest;
    private UpdateGroupRequest updateRequest;

    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(repository, mapper);

        testGroup = new Group("BTS", "HYBE", 2013);
        testGroup.setGroupId(UUID.randomUUID());
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"));
        testGroup.setLabels(Arrays.asList("kpop", "bighit"));

        testGroupDTO = GroupDTO.builder()
                .groupId(testGroup.getGroupId())
                .groupName(testGroup.getGroupName())
                .agency(testGroup.getAgency())
                .debutYear(testGroup.getDebutYear())
                .members(testGroup.getMembers())
                .labels(testGroup.getLabels())
                .build();

        createRequest = CreateGroupRequest.builder()
                .groupName("BTS")
                .agency("HYBE")
                .debutYear(2013)
                .members(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"))
                .build();

        updateRequest = UpdateGroupRequest.builder()
                .groupName("BTS")
                .agency("HYBE")
                .debutYear(2013)
                .members(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"))
                .build();
    }

    @Test
    void create_WithValidRequest_ShouldReturnGroupDTO() {
        when(mapper.toEntity(createRequest)).thenReturn(testGroup);
        when(repository.save(any(Group.class))).thenReturn(testGroup);
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        GroupDTO result = groupService.create(createRequest);

        assertThat(result).isNotNull();
        assertThat(result.getGroupName()).isEqualTo("BTS");
        verify(repository).save(any(Group.class));
    }

    @Test
    void update_WithValidRequest_ShouldReturnUpdatedGroupDTO() {
        UUID groupId = testGroup.getGroupId();
        when(repository.findById(groupId)).thenReturn(Optional.of(testGroup));
        when(repository.save(any(Group.class))).thenReturn(testGroup);
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        GroupDTO result = groupService.update(groupId, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getGroupName()).isEqualTo("BTS");
        verify(repository).save(any(Group.class));
    }

    @Test
    void findAll_ShouldReturnAllGroupDTOs() {
        when(repository.findAll()).thenReturn(Collections.singletonList(testGroup));
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        List<GroupDTO> result = groupService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGroupName()).isEqualTo("BTS");
    }

    @Test
    void findById_WhenExists_ShouldReturnGroupDTO() {
        UUID groupId = testGroup.getGroupId();
        when(repository.findById(groupId)).thenReturn(Optional.of(testGroup));
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        Optional<GroupDTO> result = groupService.findById(groupId);

        assertThat(result).isPresent();
        assertThat(result.get().getGroupName()).isEqualTo("BTS");
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        UUID groupId = UUID.randomUUID();
        when(repository.findById(groupId)).thenReturn(Optional.empty());

        Optional<GroupDTO> result = groupService.findById(groupId);

        assertThat(result).isEmpty();
    }

    @Test
    void findByAgency_ShouldReturnGroupDTOs() {
        when(repository.findByAgencyIgnoreCase("HYBE")).thenReturn(Collections.singletonList(testGroup));
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        List<GroupDTO> result = groupService.findByAgency("HYBE");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAgency()).isEqualTo("HYBE");
    }

    @Test
    void findByDebutYear_ShouldReturnGroupDTOs() {
        when(repository.findByDebutYear(2013)).thenReturn(Collections.singletonList(testGroup));
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        List<GroupDTO> result = groupService.findByDebutYear(2013);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDebutYear()).isEqualTo(2013);
    }

    @Test
    void findActiveGroups_ShouldReturnActiveGroupDTOs() {
        when(repository.findActiveGroups()).thenReturn(Collections.singletonList(testGroup));
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        List<GroupDTO> result = groupService.findActiveGroups();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDisbandYear()).isNull();
    }

    @Test
    void findDisbandedGroups_ShouldReturnDisbandedGroupDTOs() {
        Group disbandedGroup = new Group("2NE1", "YG", 2009);
        disbandedGroup.setDisbandYear(2016);
        
        GroupDTO disbandedDTO = GroupDTO.builder()
                .groupId(disbandedGroup.getGroupId())
                .groupName(disbandedGroup.getGroupName())
                .agency(disbandedGroup.getAgency())
                .debutYear(disbandedGroup.getDebutYear())
                .disbandYear(disbandedGroup.getDisbandYear())
                .build();

        when(repository.findDisbandedGroups()).thenReturn(Collections.singletonList(disbandedGroup));
        when(mapper.toDTO(disbandedGroup)).thenReturn(disbandedDTO);

        List<GroupDTO> result = groupService.findDisbandedGroups();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDisbandYear()).isEqualTo(2016);
    }

    @Test
    void findByMember_ShouldReturnGroupDTOList() {
        when(repository.findByMembersContaining("RM")).thenReturn(Collections.singletonList(testGroup));
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        List<GroupDTO> result = groupService.findByMember("RM");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMembers()).contains("RM");
    }

    @Test
    void findByLabel_ShouldReturnGroupDTOList() {
        when(repository.findByLabelsContaining("kpop")).thenReturn(Collections.singletonList(testGroup));
        when(mapper.toDTO(testGroup)).thenReturn(testGroupDTO);

        List<GroupDTO> result = groupService.findByLabel("kpop");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLabels()).contains("kpop");
    }
}
