package inkidatabase.groupservice.controller;

import inkidatabase.groupservice.model.Group;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;
    private Group testGroup;
    private UUID testId;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                                .build();
        objectMapper = new ObjectMapper();
        
        testId = UUID.randomUUID();
        testGroup = new Group("BTS", "HYBE", 2013);
        testGroup.setGroupId(testId);
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"));
        testGroup.setLabels(Arrays.asList("kpop", "boy-group"));
    }

    @Test
    void getAllGroups_ShouldReturnListOfGroups() throws Exception {
        List<Group> groups = Arrays.asList(testGroup);
        when(groupService.findAll()).thenReturn(groups);

        mockMvc.perform(get("/groups/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].groupName", is("BTS")));
    }

    @Test
    void createGroup_ShouldReturnCreatedGroup() throws Exception {
        when(groupService.create(any(Group.class))).thenReturn(testGroup);

        mockMvc.perform(post("/groups/")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(testGroup)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.groupName", is("BTS")))
               .andExpect(jsonPath("$.agency", is("HYBE")))
               .andExpect(jsonPath("$.debutYear", is(2013)));
    }

    @Test
    void getGroupById_WhenGroupExists_ShouldReturnGroup() throws Exception {
        when(groupService.findById(testId)).thenReturn(Optional.of(testGroup));

        mockMvc.perform(get("/groups/" + testId))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.groupName", is("BTS")));
    }

    @Test
    void getGroupById_WhenGroupDoesNotExist_ShouldReturn404() throws Exception {
        when(groupService.findById(testId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/groups/" + testId))
               .andExpect(status().isNotFound());
    }

    @Test
    void testGetGroupsByAgency() throws Exception {
        List<Group> groups = Arrays.asList(testGroup);
        when(groupService.findByAgency("HYBE")).thenReturn(groups);

        mockMvc.perform(get("/groups/agency/HYBE"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].groupName", is("BTS")));
    }

    @Test
    void testGetGroupsByDebutYear() throws Exception {
        int debutYear = 2013;
        when(groupService.findByDebutYear(debutYear)).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/debut-year/" + debutYear))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].groupName", is("BTS")));
    }

    @Test
    void testGetActiveGroups() throws Exception {
        when(groupService.findActiveGroups()).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/active"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].groupName", is("BTS")));
    }

    @Test
    void testGetDisbandedGroups() throws Exception {
        Group disbandedGroup = new Group("2NE1", "YG Entertainment", 2009);
        disbandedGroup.setDisbandYear(2016);
        when(groupService.findDisbandedGroups()).thenReturn(Collections.singletonList(disbandedGroup));

        mockMvc.perform(get("/groups/disbanded"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].groupName", is("2NE1")));
    }

    @Test
    void testGetGroupsByMember() throws Exception {
        Group groupWithMembers = new Group("BTS", "HYBE", 2013);
        groupWithMembers.setMembers(Arrays.asList("RM", "Jin", "Suga"));
        when(groupService.findByMember("RM")).thenReturn(Collections.singletonList(groupWithMembers));

        mockMvc.perform(get("/groups/member/RM"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].groupName", is("BTS")));
    }

    @Test
    void testGetGroupsByLabel() throws Exception {
        Group groupWithLabels = new Group("BTS", "HYBE", 2013);
        groupWithLabels.setLabels(Arrays.asList("kpop", "boy-group"));
        when(groupService.findByLabel("kpop")).thenReturn(Collections.singletonList(groupWithLabels));

        mockMvc.perform(get("/groups/label/kpop"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].groupName", is("BTS")));
    }
}
