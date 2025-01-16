package inkidatabase.groupservice.service;

import inkidatabase.groupservice.model.Group;
import java.util.List;

public interface GroupService {
    public Group create(Group group);
    public List<Group> findAll();
}
