package inkidatabase.groupservice.mapper;

import inkidatabase.groupservice.dto.CreateGroupRequest;
import inkidatabase.groupservice.dto.GroupDTO;
import inkidatabase.groupservice.dto.UpdateGroupRequest;
import inkidatabase.groupservice.model.Group;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GroupMapper {
    
    public GroupDTO toDTO(Group group) {
        return GroupDTO.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .agency(group.getAgency())
                .labels(group.getLabels())
                .members(group.getMembers())
                .formerMembers(group.getFormerMembers())
                .debutYear(group.getDebutYear())
                .disbandYear(group.getDisbandYear())
                .subunits(group.getSubunits())
                .socialLinks(group.getSocialLinks())
                .build();
    }
    
    public Group toEntity(CreateGroupRequest request) {
        Group group = new Group();
        group.setGroupName(request.getGroupName());
        group.setAgency(request.getAgency());
        group.setLabels(request.getLabels() != null ? request.getLabels() : new ArrayList<>());
        group.setMembers(request.getMembers() != null ? request.getMembers() : new ArrayList<>());
        group.setFormerMembers(request.getFormerMembers() != null ? request.getFormerMembers() : new ArrayList<>());
        group.setDebutYear(request.getDebutYear());
        if (request.getDisbandYear() != null) {
            group.setDisbandYear(request.getDisbandYear());
        }
        group.setSubunits(request.getSubunits() != null ? request.getSubunits() : new ArrayList<>());
        group.setSocialLinks(request.getSocialLinks() != null ? request.getSocialLinks() : new ArrayList<>());
        return group;
    }
    
    public void updateEntityFromRequest(Group group, UpdateGroupRequest request) {
        if (request.getGroupName() != null) {
            group.setGroupName(request.getGroupName());
        }
        if (request.getAgency() != null) {
            group.setAgency(request.getAgency());
        }
        if (request.getLabels() != null) {
            group.setLabels(request.getLabels());
        }
        if (request.getMembers() != null) {
            group.setMembers(request.getMembers());
        }
        if (request.getFormerMembers() != null) {
            group.setFormerMembers(request.getFormerMembers());
        }
        if (request.getDebutYear() != null) {
            group.setDebutYear(request.getDebutYear());
        }
        if (request.getDisbandYear() != null) {
            group.setDisbandYear(request.getDisbandYear());
        }
        if (request.getSubunits() != null) {
            group.setSubunits(request.getSubunits());
        }
        if (request.getSocialLinks() != null) {
            group.setSocialLinks(request.getSocialLinks());
        }
    }
}
