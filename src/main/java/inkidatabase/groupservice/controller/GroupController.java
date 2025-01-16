package inkidatabase.groupservice.controller;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
