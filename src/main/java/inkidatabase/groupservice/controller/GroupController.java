package inkidatabase.groupservice.controller;

import inkidatabase.groupservice.dto.CreateGroupRequest;
import inkidatabase.groupservice.dto.GroupDTO;
import inkidatabase.groupservice.dto.UpdateGroupRequest;
import inkidatabase.groupservice.service.GroupService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<GroupDTO> getAllGroups() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO createGroup(@Valid @RequestBody CreateGroupRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public GroupDTO getGroupById(@PathVariable UUID id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    String.format("Group not found with id: %s", id)
                ));
    }

    @PutMapping("/{id}")
    public GroupDTO updateGroup(@PathVariable UUID id, @Valid @RequestBody UpdateGroupRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/agency/{agency}")
    public List<GroupDTO> getGroupsByAgency(@PathVariable String agency) {
        return service.findByAgency(agency);
    }

    @GetMapping("/debut-year/{year}")
    public List<GroupDTO> getGroupsByDebutYear(@PathVariable int year) {
        return service.findByDebutYear(year);
    }

    @GetMapping("/active")
    public List<GroupDTO> getActiveGroups() {
        return service.findActiveGroups();
    }

    @GetMapping("/disbanded")
    public List<GroupDTO> getDisbandedGroups() {
        return service.findDisbandedGroups();
    }

    @GetMapping("/member/{memberName}")
    public List<GroupDTO> getGroupsByMember(@PathVariable String memberName) {
        return service.findByMember(memberName);
    }

    @GetMapping("/label/{label}")
    public List<GroupDTO> getGroupsByLabel(@PathVariable String label) {
        return service.findByLabel(label);
    }
}
