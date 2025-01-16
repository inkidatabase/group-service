package inkidatabase.groupservice.controller;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/groups")
public class GroupController {
    
    @Autowired
    private GroupService service;

    @GetMapping("/")
    public String getAllGroups(Model model) {
        List<Group> allGroups = service.findAll();
        model.addAttribute("groups", allGroups);
        return "groups";
    }

    @PostMapping("/")
    public String createGroup(@ModelAttribute Group group) {
        service.create(group);
        return "redirect:/groups/";
    }

    @GetMapping("/{id}")
    public String getGroupById(@PathVariable UUID id, Model model) {
        service.findById(id)
               .ifPresent(group -> model.addAttribute("group", group));
        return "group-details";
    }

    @GetMapping("/agency/{agency}")
    public String getGroupsByAgency(@PathVariable String agency, Model model) {
        List<Group> groups = service.findByAgency(agency);
        model.addAttribute("groups", groups);
        model.addAttribute("agency", agency);
        return "groups";
    }

    @GetMapping("/debut-year/{year}")
    public String getGroupsByDebutYear(@PathVariable int year, Model model) {
        List<Group> groups = service.findByDebutYear(year);
        model.addAttribute("groups", groups);
        model.addAttribute("debutYear", year);
        return "groups";
    }

    @GetMapping("/active")
    public String getActiveGroups(Model model) {
        List<Group> groups = service.findActiveGroups();
        model.addAttribute("groups", groups);
        model.addAttribute("status", "Active");
        return "groups";
    }

    @GetMapping("/disbanded")
    public String getDisbandedGroups(Model model) {
        List<Group> groups = service.findDisbandedGroups();
        model.addAttribute("groups", groups);
        model.addAttribute("status", "Disbanded");
        return "groups";
    }

    @GetMapping("/member/{memberName}")
    public String getGroupsByMember(@PathVariable String memberName, Model model) {
        List<Group> groups = service.findByMember(memberName);
        model.addAttribute("groups", groups);
        model.addAttribute("member", memberName);
        return "groups";
    }

    @GetMapping("/label/{label}")
    public String getGroupsByLabel(@PathVariable String label, Model model) {
        List<Group> groups = service.findByLabel(label);
        model.addAttribute("groups", groups);
        model.addAttribute("label", label);
        return "groups";
    }
}
