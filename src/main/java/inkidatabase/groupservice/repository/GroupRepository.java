package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class GroupRepository {
    private static List<Group> groupData = new ArrayList<>();

    public Group create(Group group) {
        groupData.add(group);
        return group;
    }

    public Iterator<Group> findAll() {
        return groupData.iterator();
    }
}
