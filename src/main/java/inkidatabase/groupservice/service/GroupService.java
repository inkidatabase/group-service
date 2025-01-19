package inkidatabase.groupservice.service;

import inkidatabase.groupservice.dto.CreateGroupRequest;
import inkidatabase.groupservice.dto.GroupDTO;
import inkidatabase.groupservice.dto.UpdateGroupRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupService {
    List<GroupDTO> findAll();
    
    Optional<GroupDTO> findById(UUID id);
    
    GroupDTO create(CreateGroupRequest request);
    
    GroupDTO update(UUID id, UpdateGroupRequest request);
    
    List<GroupDTO> findByAgency(String agency);
    
    List<GroupDTO> findByDebutYear(int year);
    
    List<GroupDTO> findActiveGroups();
    
    List<GroupDTO> findDisbandedGroups();
    
    List<GroupDTO> findByMember(String memberName);
    
    List<GroupDTO> findByLabel(String label);
}
