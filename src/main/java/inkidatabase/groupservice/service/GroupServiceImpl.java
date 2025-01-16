package inkidatabase.groupservice.service;

import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group create(Group group) {
        return groupRepository.create(group);
    }

    @Override
    public List<Group> findAll() {
        Iterator<Group> groupIterator = groupRepository.findAll();
        List<Group> allGroups = new ArrayList<>();
        groupIterator.forEachRemaining(allGroups::add);
        return allGroups;
    }

    @Override
    public Optional<Group> findById(UUID groupId) {
        Iterator<Group> iterator = groupRepository.findById(groupId);
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    @Override
    public List<Group> findByAgency(String agency) {
        Iterator<Group> iterator = groupRepository.findByAgency(agency);
        List<Group> groups = new ArrayList<>();
        iterator.forEachRemaining(groups::add);
        return groups;
    }

    @Override
    public List<Group> findByDebutYear(int year) {
        Iterator<Group> iterator = groupRepository.findByDebutYear(year);
        List<Group> groups = new ArrayList<>();
        iterator.forEachRemaining(groups::add);
        return groups;
    }

    @Override
    public List<Group> findActiveGroups() {
        Iterator<Group> iterator = groupRepository.findActiveGroups();
        List<Group> groups = new ArrayList<>();
        iterator.forEachRemaining(groups::add);
        return groups;
    }

    @Override
    public List<Group> findDisbandedGroups() {
        Iterator<Group> iterator = groupRepository.findDisbandedGroups();
        List<Group> groups = new ArrayList<>();
        iterator.forEachRemaining(groups::add);
        return groups;
    }

    @Override
    public List<Group> findByMember(String memberName) {
        Iterator<Group> iterator = groupRepository.findByMember(memberName);
        List<Group> groups = new ArrayList<>();
        iterator.forEachRemaining(groups::add);
        return groups;
    }

    @Override
    public List<Group> findByLabel(String label) {
        Iterator<Group> iterator = groupRepository.findByLabel(label);
        List<Group> groups = new ArrayList<>();
        iterator.forEachRemaining(groups::add);
        return groups;
    }
}
