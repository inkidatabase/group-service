package inkidatabase.groupservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import inkidatabase.groupservice.dto.CreateGroupRequest;
import inkidatabase.groupservice.dto.GroupDTO;
import inkidatabase.groupservice.dto.UpdateGroupRequest;
import inkidatabase.groupservice.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;
    private GroupDTO testGroupDTO;
    private List<GroupDTO> groupDTOs;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .build();
        
        objectMapper = new ObjectMapper();

        testGroupDTO = GroupDTO.builder()
                .groupId(UUID.randomUUID())
                .groupName("BTS")
                .agency("HYBE")
                .debutYear(2013)
                .members(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"))
                .labels(Arrays.asList("kpop", "bighit"))
                .build();

        groupDTOs = Arrays.asList(testGroupDTO);
    }

    @Test
    void getAllGroups_ReturnsGroupList() throws Exception {
        when(groupService.findAll()).thenReturn(groupDTOs);

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupName").value("BTS"));
    }

    @Test
    void createGroup_ReturnsCreatedGroup() throws Exception {
        CreateGroupRequest request = CreateGroupRequest.builder()
                .groupName("BTS")
                .agency("HYBE")
                .debutYear(2013)
                .members(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"))
                .build();

        when(groupService.create(any(CreateGroupRequest.class))).thenReturn(testGroupDTO);

        mockMvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.groupName").value("BTS"));
    }

    @Test
    void getGroupById_ReturnsGroup() throws Exception {
        UUID testId = testGroupDTO.getGroupId();
        when(groupService.findById(testId)).thenReturn(Optional.of(testGroupDTO));

        mockMvc.perform(get("/groups/" + testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("BTS"));
    }

    @Test
    void getGroupById_NotFound_Returns404() throws Exception {
        UUID testId = UUID.randomUUID();
        when(groupService.findById(testId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/groups/" + testId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateGroup_ReturnsUpdatedGroup() throws Exception {
        UUID testId = testGroupDTO.getGroupId();
        UpdateGroupRequest request = UpdateGroupRequest.builder()
                .groupName("BTS")
                .agency("HYBE")
                .debutYear(2013)
                .members(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"))
                .build();

        when(groupService.update(any(UUID.class), any(UpdateGroupRequest.class))).thenReturn(testGroupDTO);

        mockMvc.perform(put("/groups/" + testId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("BTS"));
    }

    @Test
    void getGroupsByAgency_ReturnsGroupList() throws Exception {
        when(groupService.findByAgency("HYBE")).thenReturn(groupDTOs);

        mockMvc.perform(get("/groups/agency/HYBE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("BTS"));
    }

    @Test
    void getGroupsByDebutYear_ReturnsGroupList() throws Exception {
        int debutYear = 2013;
        when(groupService.findByDebutYear(debutYear)).thenReturn(Collections.singletonList(testGroupDTO));

        mockMvc.perform(get("/groups/debut-year/" + debutYear))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("BTS"));
    }

    @Test
    void getActiveGroups_ReturnsGroupList() throws Exception {
        when(groupService.findActiveGroups()).thenReturn(Collections.singletonList(testGroupDTO));

        mockMvc.perform(get("/groups/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("BTS"));
    }

    @Test
    void getDisbandedGroups_ReturnsGroupList() throws Exception {
        GroupDTO disbandedGroupDTO = GroupDTO.builder()
                .groupId(UUID.randomUUID())
                .groupName("2NE1")
                .agency("YG")
                .debutYear(2009)
                .disbandYear(2016)
                .build();

        when(groupService.findDisbandedGroups()).thenReturn(Collections.singletonList(disbandedGroupDTO));

        mockMvc.perform(get("/groups/disbanded"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("2NE1"));
    }

    @Test
    void getGroupsByMember_ReturnsGroupList() throws Exception {
        when(groupService.findByMember("RM")).thenReturn(Collections.singletonList(testGroupDTO));

        mockMvc.perform(get("/groups/member/RM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("BTS"));
    }

    @Test
    void getGroupsByLabel_ReturnsGroupList() throws Exception {
        when(groupService.findByLabel("kpop")).thenReturn(Collections.singletonList(testGroupDTO));

        mockMvc.perform(get("/groups/label/kpop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("BTS"));
    }
}
