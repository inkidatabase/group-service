package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GroupRepository {
    private final List<Group> groupData = new ArrayList<>();

    public Group create(Group group) {
        groupData.add(group);
        return group;
    }

    public Iterator<Group> findAll() {
        return new ArrayList<>(groupData).iterator();
    }

    public Iterator<Group> findById(UUID groupId) {
        return groupData.stream()
                .filter(group -> group.getGroupId().equals(groupId))
                .iterator();
    }

    public Iterator<Group> findByAgency(String agency) {
        return groupData.stream()
                .filter(group -> group.getAgency().equalsIgnoreCase(agency))
                .iterator();
    }

    public Iterator<Group> findByDebutYear(int year) {
        return groupData.stream()
                .filter(group -> group.getDebutYear() == year)
                .iterator();
    }

    public Iterator<Group> findActiveGroups() {
        return groupData.stream()
                .filter(group -> group.getDisbandYear() == 0)
                .iterator();
    }

    public Iterator<Group> findDisbandedGroups() {
        return groupData.stream()
                .filter(group -> group.getDisbandYear() > 0)
                .iterator();
    }

    public Iterator<Group> findByMember(String memberName) {
        return groupData.stream()
                .filter(group -> group.getMembers().contains(memberName) || 
                               group.getFormerMembers().contains(memberName))
                .iterator();
    }

    public Iterator<Group> findByLabel(String label) {
        return groupData.stream()
                .filter(group -> group.getLabels().contains(label))
                .iterator();
    }
}
