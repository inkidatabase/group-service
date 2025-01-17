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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.*;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;
    private Group testGroup;
    private UUID testId;
    private static final String GROUPS_VIEW = GroupController.GROUPS_VIEW;
    private static final String GROUP_DETAILS_VIEW = GroupController.GROUP_DETAILS_VIEW;

    @BeforeEach
    void setUp() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty(ResponseStatusException.class.getName(), "error");
        exceptionResolver.setExceptionMappings(mappings);
        exceptionResolver.setDefaultErrorView("error");
        exceptionResolver.setDefaultStatusCode(HttpStatus.NOT_FOUND.value());
        exceptionResolver.setWarnLogCategory("warn");

        mockMvc = MockMvcBuilders
            .standaloneSetup(groupController)
            .setHandlerExceptionResolvers(exceptionResolver)
            .build();
            
        testGroup = Group.builder("BTS", "BigHit Music", 2013).build();
        testId = testGroup.getGroupId();
    }

    @Test
    void testGetAllGroups() throws Exception {
        List<Group> groups = Arrays.asList(
            testGroup,
            Group.builder("TXT", "BigHit Music", 2019).build()
        );
        when(groupService.findAll()).thenReturn(groups);

        mockMvc.perform(get("/groups/"))
               .andExpect(status().isOk())
               .andExpect(view().name(GROUPS_VIEW))
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
               .andExpect(view().name(GROUP_DETAILS_VIEW))
               .andExpect(model().attribute("group", testGroup));
    }

    @Test
    void testGetGroupByIdNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        when(groupService.findById(nonExistentId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/groups/{id}", nonExistentId))
               .andExpect(status().isNotFound())
               .andExpect(result -> {
                   Throwable ex = result.getResolvedException();
                   assertTrue(ex instanceof ResponseStatusException, 
                       "Expected ResponseStatusException but got " + (ex != null ? ex.getClass().getName() : "null"));
                   ResponseStatusException responseEx = (ResponseStatusException) ex;
                   assertEquals(HttpStatus.NOT_FOUND, responseEx.getStatusCode(), 
                       "Expected status code NOT_FOUND but got " + responseEx.getStatusCode());
                   String expectedMessage = String.format("Group not found with id: %s", nonExistentId);
                   assertTrue(responseEx.getReason().contains(expectedMessage), 
                       String.format("Expected message to contain '%s' but was '%s'", expectedMessage, responseEx.getReason()));
               });
    }

    @Test
    void testGetGroupsByAgency() throws Exception {
        List<Group> groups = Arrays.asList(
            testGroup,
            Group.builder("TXT", "BigHit Music", 2019).build()
        );
        when(groupService.findByAgency("BigHit Music")).thenReturn(groups);

        mockMvc.perform(get("/groups/agency/{agency}", "BigHit Music"))
               .andExpect(status().isOk())
               .andExpect(view().name(GROUPS_VIEW))
               .andExpect(model().attribute("groups", groups))
               .andExpect(model().attribute("agency", "BigHit Music"));
    }

    @Test
    void testGetGroupsByDebutYear() throws Exception {
        int debutYear = 2013;
        when(groupService.findByDebutYear(debutYear)).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/debut-year/{year}", debutYear))
               .andExpect(status().isOk())
               .andExpect(view().name(GROUPS_VIEW))
               .andExpect(model().attribute("groups", Collections.singletonList(testGroup)))
               .andExpect(model().attribute("debutYear", debutYear));
    }

    @Test
    void testGetActiveGroups() throws Exception {
        when(groupService.findActiveGroups()).thenReturn(Collections.singletonList(testGroup));

        mockMvc.perform(get("/groups/active"))
               .andExpect(status().isOk())
               .andExpect(view().name(GROUPS_VIEW))
               .andExpect(model().attribute("groups", Collections.singletonList(testGroup)))
               .andExpect(model().attribute("status", "Active"));
    }

    @Test
    void testGetDisbandedGroups() throws Exception {
        Group disbandedGroup = Group.builder("2NE1", "YG Entertainment", 2009)
                                  .disbandYear(2016)
                                  .build();
        when(groupService.findDisbandedGroups()).thenReturn(Collections.singletonList(disbandedGroup));

        mockMvc.perform(get("/groups/disbanded"))
               .andExpect(status().isOk())
               .andExpect(view().name(GROUPS_VIEW))
               .andExpect(model().attribute("groups", Collections.singletonList(disbandedGroup)))
               .andExpect(model().attribute("status", "Disbanded"));
    }

    @Test
    void testGetGroupsByMember() throws Exception {
        Group groupWithMembers = Group.builder("BTS", "BigHit Music", 2013)
                                    .members(Arrays.asList("RM", "Jin", "Suga"))
                                    .build();
        when(groupService.findByMember("RM")).thenReturn(Collections.singletonList(groupWithMembers));

        mockMvc.perform(get("/groups/member/{memberName}", "RM"))
               .andExpect(status().isOk())
               .andExpect(view().name(GROUPS_VIEW))
               .andExpect(model().attribute("groups", Collections.singletonList(groupWithMembers)))
               .andExpect(model().attribute("member", "RM"));
    }

    @Test
    void testGetGroupsByLabel() throws Exception {
        Group groupWithLabels = Group.builder("BTS", "BigHit Music", 2013)
                                   .labels(Arrays.asList("HYBE", "BigHit Music"))
                                   .build();
        when(groupService.findByLabel("HYBE")).thenReturn(Collections.singletonList(groupWithLabels));

        mockMvc.perform(get("/groups/label/{label}", "HYBE"))
               .andExpect(status().isOk())
               .andExpect(view().name(GROUPS_VIEW))
               .andExpect(model().attribute("groups", Collections.singletonList(groupWithLabels)))
               .andExpect(model().attribute("label", "HYBE"));
    }
}
