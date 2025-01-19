package inkidatabase.groupservice.controller;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {
    
    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Group> getAllGroups() {
        return service.findAll();
    }

    @PostMapping("/")
    public Group createGroup(@RequestBody Group group) {
        return service.create(group);
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable UUID id) {
        return service.findById(id)
                     .orElseThrow(() -> new ResponseStatusException(
                         HttpStatus.NOT_FOUND, 
                         String.format("Group not found with id: %s", id)
                     ));
    }

    @GetMapping("/agency/{agency}")
    public List<Group> getGroupsByAgency(@PathVariable String agency) {
        return service.findByAgency(agency);
    }

    @GetMapping("/debut-year/{year}")
    public List<Group> getGroupsByDebutYear(@PathVariable int year) {
        return service.findByDebutYear(year);
    }

    @GetMapping("/active")
    public List<Group> getActiveGroups() {
        return service.findActiveGroups();
    }

    @GetMapping("/disbanded")
    public List<Group> getDisbandedGroups() {
        return service.findDisbandedGroups();
    }

    @GetMapping("/member/{memberName}")
    public List<Group> getGroupsByMember(@PathVariable String memberName) {
        return service.findByMember(memberName);
    }

    @GetMapping("/label/{label}")
    public List<Group> getGroupsByLabel(@PathVariable String label) {
        return service.findByLabel(label);
    }
}
