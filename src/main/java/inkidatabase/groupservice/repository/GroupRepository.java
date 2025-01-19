package inkidatabase.groupservice.repository;

import inkidatabase.groupservice.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    
    List<Group> findByAgencyIgnoreCase(String agency);
    
    List<Group> findByDebutYear(int year);
    
    @Query("SELECT g FROM Group g WHERE g.disbandYear = 0")
    List<Group> findActiveGroups();
    
    @Query("SELECT g FROM Group g WHERE g.disbandYear > 0")
    List<Group> findDisbandedGroups();
    
    @Query("SELECT g FROM Group g WHERE :memberName MEMBER OF g.members OR :memberName MEMBER OF g.formerMembers")
    List<Group> findByMember(@Param("memberName") String memberName);
    
    @Query("SELECT g FROM Group g WHERE :label MEMBER OF g.labels")
    List<Group> findByLabel(@Param("label") String label);
}
