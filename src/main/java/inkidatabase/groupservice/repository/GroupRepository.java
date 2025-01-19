package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    
    List<Group> findByAgencyIgnoreCase(String agency);
    
    List<Group> findByDebutYear(int year);

    List<Group> findByLabelsContaining(String label);

    List<Group> findByMembersContaining(String member);
    
    @Query("SELECT g FROM Group g WHERE g.disbandYear = 0")
    List<Group> findActiveGroups();

    List<Group> findByDisbandYearGreaterThan(int year);

    @Query("SELECT g FROM Group g WHERE g.disbandYear > 0")
    List<Group> findDisbandedGroups();
}
