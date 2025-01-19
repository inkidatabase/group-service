package inkidatabase.groupservice.service;

import inkidatabase.groupservice.dto.CreateGroupRequest;
import inkidatabase.groupservice.dto.GroupDTO;
import inkidatabase.groupservice.dto.UpdateGroupRequest;
import inkidatabase.groupservice.mapper.GroupMapper;
import inkidatabase.groupservice.model.Group;
import inkidatabase.groupservice.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    
    private final GroupRepository repository;
    private final GroupMapper mapper;

    public GroupServiceImpl(GroupRepository repository, GroupMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<GroupDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public Optional<GroupDTO> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public GroupDTO create(CreateGroupRequest request) {
        Group group = mapper.toEntity(request);
        Group savedGroup = repository.save(group);
        return mapper.toDTO(savedGroup);
    }

    @Override
    public GroupDTO update(UUID id, UpdateGroupRequest request) {
        Group existingGroup = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + id));
        
        mapper.updateEntityFromRequest(existingGroup, request);
        Group updatedGroup = repository.save(existingGroup);
        return mapper.toDTO(updatedGroup);
    }

    @Override
    public List<GroupDTO> findByAgency(String agency) {
        return repository.findByAgencyIgnoreCase(agency).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<GroupDTO> findByDebutYear(int year) {
        return repository.findByDebutYear(year).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<GroupDTO> findActiveGroups() {
        return repository.findActiveGroups().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<GroupDTO> findDisbandedGroups() {
        return repository.findDisbandedGroups().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<GroupDTO> findByMember(String memberName) {
        return repository.findByMembersContaining(memberName).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<GroupDTO> findByLabel(String label) {
        return repository.findByLabelsContaining(label).stream()
                .map(mapper::toDTO)
                .toList();
    }
}
