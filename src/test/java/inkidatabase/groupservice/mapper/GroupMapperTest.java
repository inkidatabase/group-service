package inkidatabase.groupservice.mapper;

import inkidatabase.groupservice.dto.CreateGroupRequest;
import inkidatabase.groupservice.dto.GroupDTO;
import inkidatabase.groupservice.dto.UpdateGroupRequest;
import inkidatabase.groupservice.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GroupMapperTest {

    private GroupMapper mapper;
    private Group testGroup;
    private CreateGroupRequest createRequest;
    private UpdateGroupRequest updateRequest;

    @BeforeEach
    void setUp() {
        mapper = new GroupMapper();

        // Set up test group
        testGroup = new Group("BTS", "HYBE", 2013);
        testGroup.setGroupId(UUID.randomUUID());
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"));
        testGroup.setLabels(Arrays.asList("kpop", "bighit"));
        testGroup.setFormerMembers(Arrays.asList("Former1"));
        testGroup.setDisbandYear(2023);
        testGroup.setSubunits(Arrays.asList("Subunit1"));
        testGroup.setSocialLinks(Arrays.asList("Link1"));

        // Set up create request
        createRequest = CreateGroupRequest.builder()
                .groupName("NewGroup")
                .agency("NewAgency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Subunit1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        // Set up update request
        updateRequest = UpdateGroupRequest.builder()
                .groupName("UpdatedGroup")
                .agency("UpdatedAgency")
                .members(Arrays.asList("UpdatedMember1", "UpdatedMember2"))
                .labels(Arrays.asList("UpdatedLabel1"))
                .formerMembers(Arrays.asList("UpdatedFormer1"))
                .debutYear(2021)
                .disbandYear(2024)
                .subunits(Arrays.asList("UpdatedSubunit1"))
                .socialLinks(Arrays.asList("UpdatedLink1"))
                .build();
    }

    @Test
    void toDTO_ShouldMapAllFields() {
        GroupDTO dto = mapper.toDTO(testGroup);

        assertThat(dto.getGroupId()).isEqualTo(testGroup.getGroupId());
        assertThat(dto.getGroupName()).isEqualTo(testGroup.getGroupName());
        assertThat(dto.getAgency()).isEqualTo(testGroup.getAgency());
        assertThat(dto.getMembers()).containsExactlyElementsOf(testGroup.getMembers());
        assertThat(dto.getLabels()).containsExactlyElementsOf(testGroup.getLabels());
        assertThat(dto.getFormerMembers()).containsExactlyElementsOf(testGroup.getFormerMembers());
        assertThat(dto.getDebutYear()).isEqualTo(testGroup.getDebutYear());
        assertThat(dto.getDisbandYear()).isEqualTo(testGroup.getDisbandYear());
        assertThat(dto.getSubunits()).containsExactlyElementsOf(testGroup.getSubunits());
        assertThat(dto.getSocialLinks()).containsExactlyElementsOf(testGroup.getSocialLinks());
    }

    @Test
    void toEntity_FromCreateRequest_ShouldMapAllFields() {
        Group entity = mapper.toEntity(createRequest);

        assertThat(entity.getGroupName()).isEqualTo(createRequest.getGroupName());
        assertThat(entity.getAgency()).isEqualTo(createRequest.getAgency());
        assertThat(entity.getMembers()).containsExactlyElementsOf(createRequest.getMembers());
        assertThat(entity.getLabels()).containsExactlyElementsOf(createRequest.getLabels());
        assertThat(entity.getFormerMembers()).containsExactlyElementsOf(createRequest.getFormerMembers());
        assertThat(entity.getDebutYear()).isEqualTo(createRequest.getDebutYear());
        assertThat(entity.getDisbandYear()).isEqualTo(createRequest.getDisbandYear());
        assertThat(entity.getSubunits()).containsExactlyElementsOf(createRequest.getSubunits());
        assertThat(entity.getSocialLinks()).containsExactlyElementsOf(createRequest.getSocialLinks());
    }

    @Test
    void toEntity_FromCreateRequest_WithNullFields_ShouldMapRequiredFields() {
        CreateGroupRequest requestWithNulls = CreateGroupRequest.builder()
                .groupName("NewGroup")
                .agency("NewAgency")
                .debutYear(2020)
                .build();

        Group entity = mapper.toEntity(requestWithNulls);

        assertThat(entity.getGroupName()).isEqualTo(requestWithNulls.getGroupName());
        assertThat(entity.getAgency()).isEqualTo(requestWithNulls.getAgency());
        assertThat(entity.getDebutYear()).isEqualTo(requestWithNulls.getDebutYear());
        assertThat(entity.getMembers()).isNotNull().isEmpty();
        assertThat(entity.getLabels()).isNotNull().isEmpty();
        assertThat(entity.getFormerMembers()).isNotNull().isEmpty();
        assertThat(entity.getSubunits()).isNotNull().isEmpty();
        assertThat(entity.getSocialLinks()).isNotNull().isEmpty();
    }

    @Test
    void updateEntityFromRequest_ShouldUpdateAllFields() {
        Group entity = new Group("Original", "OriginalAgency", 2019);
        entity.setMembers(Arrays.asList("OriginalMember"));
        entity.setLabels(Arrays.asList("OriginalLabel"));
        entity.setFormerMembers(Arrays.asList("OriginalFormer"));
        entity.setDisbandYear(2022);
        entity.setSubunits(Arrays.asList("OriginalSubunit"));
        entity.setSocialLinks(Arrays.asList("OriginalLink"));

        mapper.updateEntityFromRequest(entity, updateRequest);

        assertThat(entity.getGroupName()).isEqualTo(updateRequest.getGroupName());
        assertThat(entity.getAgency()).isEqualTo(updateRequest.getAgency());
        assertThat(entity.getMembers()).containsExactlyElementsOf(updateRequest.getMembers());
        assertThat(entity.getLabels()).containsExactlyElementsOf(updateRequest.getLabels());
        assertThat(entity.getFormerMembers()).containsExactlyElementsOf(updateRequest.getFormerMembers());
        assertThat(entity.getDebutYear()).isEqualTo(updateRequest.getDebutYear());
        assertThat(entity.getDisbandYear()).isEqualTo(updateRequest.getDisbandYear());
        assertThat(entity.getSubunits()).containsExactlyElementsOf(updateRequest.getSubunits());
        assertThat(entity.getSocialLinks()).containsExactlyElementsOf(updateRequest.getSocialLinks());
    }

    @Test
    void updateEntityFromRequest_WithNullFields_ShouldNotUpdateNullFields() {
        Group entity = new Group("Original", "OriginalAgency", 2019);
        entity.setMembers(Arrays.asList("OriginalMember"));
        entity.setLabels(Arrays.asList("OriginalLabel"));
        entity.setFormerMembers(Arrays.asList("OriginalFormer"));
        entity.setDisbandYear(2022);
        entity.setSubunits(Arrays.asList("OriginalSubunit"));
        entity.setSocialLinks(Arrays.asList("OriginalLink"));

        UpdateGroupRequest partialRequest = UpdateGroupRequest.builder()
                .groupName("UpdatedGroup")
                .agency(null)
                .members(null)
                .labels(null)
                .formerMembers(null)
                .debutYear(null)
                .disbandYear(null)
                .subunits(null)
                .socialLinks(null)
                .build();

        mapper.updateEntityFromRequest(entity, partialRequest);

        assertThat(entity.getGroupName()).isEqualTo(partialRequest.getGroupName());
        assertThat(entity.getAgency()).isEqualTo("OriginalAgency");
        assertThat(entity.getMembers()).containsExactly("OriginalMember");
        assertThat(entity.getLabels()).containsExactly("OriginalLabel");
        assertThat(entity.getFormerMembers()).containsExactly("OriginalFormer");
        assertThat(entity.getDebutYear()).isEqualTo(2019);
        assertThat(entity.getDisbandYear()).isEqualTo(2022);
        assertThat(entity.getSubunits()).containsExactly("OriginalSubunit");
        assertThat(entity.getSocialLinks()).containsExactly("OriginalLink");
    }

    @Test
    void updateEntityFromRequest_WithAllNullFields_ShouldNotUpdateAnyFields() {
        Group originalGroup = testGroup;
        String originalName = originalGroup.getGroupName();
        String originalAgency = originalGroup.getAgency();
        Integer originalDebutYear = originalGroup.getDebutYear();
        
        UpdateGroupRequest requestWithNulls = UpdateGroupRequest.builder().build();
        
        mapper.updateEntityFromRequest(originalGroup, requestWithNulls);
        
        assertThat(originalGroup.getGroupName()).isEqualTo(originalName);
        assertThat(originalGroup.getAgency()).isEqualTo(originalAgency);
        assertThat(originalGroup.getDebutYear()).isEqualTo(originalDebutYear);
        assertThat(originalGroup.getMembers()).isEqualTo(testGroup.getMembers());
        assertThat(originalGroup.getLabels()).isEqualTo(testGroup.getLabels());
        assertThat(originalGroup.getFormerMembers()).isEqualTo(testGroup.getFormerMembers());
        assertThat(originalGroup.getDisbandYear()).isEqualTo(testGroup.getDisbandYear());
        assertThat(originalGroup.getSubunits()).isEqualTo(testGroup.getSubunits());
        assertThat(originalGroup.getSocialLinks()).isEqualTo(testGroup.getSocialLinks());
    }

    @Test
    void updateEntityFromRequest_WithMixedNullAndNonNullFields_ShouldUpdateOnlyNonNullFields() {
        Group originalGroup = testGroup;
        UpdateGroupRequest partialRequest = UpdateGroupRequest.builder()
                .groupName("UpdatedName")
                .members(Arrays.asList("NewMember1"))
                .build();
        
        mapper.updateEntityFromRequest(originalGroup, partialRequest);
        
        assertThat(originalGroup.getGroupName()).isEqualTo("UpdatedName");
        assertThat(originalGroup.getMembers()).containsExactly("NewMember1");
        assertThat(originalGroup.getAgency()).isEqualTo(testGroup.getAgency());
        assertThat(originalGroup.getLabels()).isEqualTo(testGroup.getLabels());
        assertThat(originalGroup.getFormerMembers()).isEqualTo(testGroup.getFormerMembers());
        assertThat(originalGroup.getDebutYear()).isEqualTo(testGroup.getDebutYear());
        assertThat(originalGroup.getDisbandYear()).isEqualTo(testGroup.getDisbandYear());
        assertThat(originalGroup.getSubunits()).isEqualTo(testGroup.getSubunits());
        assertThat(originalGroup.getSocialLinks()).isEqualTo(testGroup.getSocialLinks());
    }
}
