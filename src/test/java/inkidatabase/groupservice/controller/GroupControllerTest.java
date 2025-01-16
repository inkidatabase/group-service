package inkidatabase.groupservice.controller;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;
    private Group testGroup;
    private UUID testId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
        testGroup = new Group("BTS", "BigHit Music", 2013);
        testId = testGroup.getGroupId();
    }

    @Test
    void testGetAllGroups() throws Exception {
        List<Group> groups = Arrays.asList(
            testGroup,
            new Group("TXT", "BigHit Music", 2019)
        );
        when(groupService.findAll()).thenReturn(groups);

        mockMvc.perform(get("/groups/"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups"))
               .andExpect(model().attribute("groups", groups));
    }

    @Test
    void testCreateGroup() throws Exception {
        when(groupService.create(any(Group.class))).thenReturn(testGroup);

        mockMvc.perform(post("/groups/")
               .flashAttr("group", testGroup))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/groups/"));
    }

    @Test
    void testGetGroupById() throws Exception {
        when(groupService.findById(testId)).thenReturn(Optional.of(testGroup));

        mockMvc.perform(get("/groups/{id}", testId))
               .andExpect(status().isOk())
               .andExpect(view().name("group-details"))
               .andExpect(model().attribute("group", testGroup));
    }

    @Test
    void testGetGroupsByAgency() throws Exception {
        List<Group> groups = Arrays.asList(testGroup, new Group("TXT", "BigHit Music", 2019));
        when(groupService.findByAgency("BigHit Music")).thenReturn(groups);

        mockMvc.perform(get("/groups/agency/{agency}", "BigHit Music"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups"))
               .andExpect(model().attribute("groups", groups))
               .andExpect(model().attribute("agency", "BigHit Music"));
    }

    @Test
    void testGetGroupsByDebutYear() throws Exception {
        when(groupService.findByDebutYear(2013)).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/debut-year/{year}", 2013))
               .andExpect(status().isOk())
               .andExpect(view().name("groups"))
               .andExpect(model().attribute("groups", Collections.singletonList(testGroup)))
               .andExpect(model().attribute("debutYear", 2013));
    }

    @Test
    void testGetActiveGroups() throws Exception {
        when(groupService.findActiveGroups()).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/active"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups"))
               .andExpect(model().attribute("groups", Collections.singletonList(testGroup)))
               .andExpect(model().attribute("status", "Active"));
    }

    @Test
    void testGetDisbandedGroups() throws Exception {
        Group disbandedGroup = new Group("2NE1", "YG Entertainment", 2009);
        disbandedGroup.setDisbandYear(2016);
        when(groupService.findDisbandedGroups()).thenReturn(Collections.singletonList(disbandedGroup));

        mockMvc.perform(get("/groups/disbanded"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups"))
               .andExpect(model().attribute("groups", Collections.singletonList(disbandedGroup)))
               .andExpect(model().attribute("status", "Disbanded"));
    }

    @Test
    void testGetGroupsByMember() throws Exception {
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga"));
        when(groupService.findByMember("RM")).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/member/{memberName}", "RM"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups"))
               .andExpect(model().attribute("groups", Collections.singletonList(testGroup)))
               .andExpect(model().attribute("member", "RM"));
    }

    @Test
    void testGetGroupsByLabel() throws Exception {
        testGroup.setLabels(Arrays.asList("HYBE", "BigHit Music"));
        when(groupService.findByLabel("HYBE")).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/label/{label}", "HYBE"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups"))
               .andExpect(model().attribute("groups", Collections.singletonList(testGroup)))
               .andExpect(model().attribute("label", "HYBE"));
    }
}
