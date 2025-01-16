package inkidatabase.groupservice.service;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group create(Group group) {
        groupRepository.create(group);
        return group;
    }

    @Override
    public List<Group> findAll() {
        Iterator<Group> groupIterator = groupRepository.findAll();
        List<Group> allGroups = new ArrayList<>();
        groupIterator.forEachRemaining(allGroups::add);
        return allGroups;
    }
    
}
