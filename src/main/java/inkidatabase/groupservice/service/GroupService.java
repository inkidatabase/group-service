package inkidatabase.groupservice.service;

import inkidatabase.groupservice.model.Group;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupService {
    Group create(Group group);
    List<Group> findAll();
    Optional<Group> findById(UUID groupId);
    List<Group> findByAgency(String agency);
    List<Group> findByDebutYear(int year);
    List<Group> findActiveGroups();
    List<Group> findDisbandedGroups();
    List<Group> findByMember(String memberName);
    List<Group> findByLabel(String label);
}
