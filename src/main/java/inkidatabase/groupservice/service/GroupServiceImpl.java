package inkidatabase.groupservice.service;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group create(Group group) {
        if (group.getGroupId() == null) {
            group.setGroupId(UUID.randomUUID());
        }
        return groupRepository.save(group);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> findById(UUID groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    public List<Group> findByAgency(String agency) {
        return groupRepository.findByAgencyIgnoreCase(agency);
    }

    @Override
    public List<Group> findByDebutYear(int year) {
        return groupRepository.findByDebutYear(year);
    }

    @Override
    public List<Group> findActiveGroups() {
        return groupRepository.findActiveGroups();
    }

    @Override
    public List<Group> findDisbandedGroups() {
        return groupRepository.findDisbandedGroups();
    }

    @Override
    public List<Group> findByMember(String memberName) {
        return groupRepository.findByMember(memberName);
    }

    @Override
    public List<Group> findByLabel(String label) {
        return groupRepository.findByLabel(label);
    }
}
