package inkidatabase.groupservice.controller;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/groups")
public class GroupController {
    
    private final GroupService service;
    public static final String GROUPS_VIEW = "groups";
    public static final String GROUP_DETAILS_VIEW = "group-details";

    public GroupController(GroupService service) {
        this.service = service;
    }

    private String prepareGroupListView(List<Group> groups, Model model) {
        model.addAttribute(GROUPS_VIEW, groups);
        return GROUPS_VIEW;
    }

    private String prepareGroupListView(List<Group> groups, String attributeName, Object attributeValue, Model model) {
        model.addAttribute(GROUPS_VIEW, groups);
        model.addAttribute(attributeName, attributeValue);
        return GROUPS_VIEW;
    }

    @GetMapping("/")
    public String getAllGroups(Model model) {
        return prepareGroupListView(service.findAll(), model);
    }

    @PostMapping("/")
    public String createGroup(@ModelAttribute Group group) {
        service.create(group);
        return "redirect:/groups/";
    }

    @GetMapping("/{id}")
    public String getGroupById(@PathVariable UUID id, Model model) {
        return service.findById(id)
                     .map(group -> {
                         model.addAttribute("group", group);
                         return GROUP_DETAILS_VIEW;
                     })
                     .orElseThrow(() -> new ResponseStatusException(
                         HttpStatus.NOT_FOUND, 
                         String.format("Group not found with id: %s", id)
                     ));
    }

    @GetMapping("/agency/{agency}")
    public String getGroupsByAgency(@PathVariable String agency, Model model) {
        return prepareGroupListView(service.findByAgency(agency), "agency", agency, model);
    }

    @GetMapping("/debut-year/{year}")
    public String getGroupsByDebutYear(@PathVariable int year, Model model) {
        return prepareGroupListView(service.findByDebutYear(year), "debutYear", year, model);
    }

    @GetMapping("/active")
    public String getActiveGroups(Model model) {
        return prepareGroupListView(service.findActiveGroups(), "status", "Active", model);
    }

    @GetMapping("/disbanded")
    public String getDisbandedGroups(Model model) {
        return prepareGroupListView(service.findDisbandedGroups(), "status", "Disbanded", model);
    }

    @GetMapping("/member/{memberName}")
    public String getGroupsByMember(@PathVariable String memberName, Model model) {
        return prepareGroupListView(service.findByMember(memberName), "member", memberName, model);
    }

    @GetMapping("/label/{label}")
    public String getGroupsByLabel(@PathVariable String label, Model model) {
        return prepareGroupListView(service.findByLabel(label), "label", label, model);
    }
}
