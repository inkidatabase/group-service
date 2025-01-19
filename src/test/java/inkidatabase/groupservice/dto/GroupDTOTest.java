package inkidatabase.groupservice.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.UUID;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class GroupDTOTest {

    @Test
    void testEqualsAndHashCode() {
        UUID groupId = UUID.randomUUID();
        List<String> members = Arrays.asList("Member1", "Member2");
        List<String> labels = Arrays.asList("Label1", "Label2");
        List<String> formerMembers = Arrays.asList("Former1");
        List<String> subunits = Arrays.asList("Subunit1");
        List<String> socialLinks = Arrays.asList("link1");

        GroupDTO dto1 = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .labels(labels)
                .formerMembers(formerMembers)
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(subunits)
                .socialLinks(socialLinks)
                .build();

        // Test same instance equality
        assertThat(dto1).isEqualTo(dto1);
        assertThat(dto1.hashCode()).isEqualTo(dto1.hashCode());

        // Test null
        assertThat(dto1).isNotEqualTo(null);

        // Test different type
        assertThat(dto1).isNotEqualTo(new Object());

        // Test identical object
        GroupDTO dto2 = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .labels(labels)
                .formerMembers(formerMembers)
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(subunits)
                .socialLinks(socialLinks)
                .build();
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());

        // Test each field difference
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(UUID.randomUUID()).build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Different").build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Different").build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Test Agency")
                .members(Arrays.asList("Different")).build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Test Agency")
                .members(members).labels(Arrays.asList("Different")).build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Test Agency")
                .members(members).labels(labels).formerMembers(Arrays.asList("Different")).build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Test Agency")
                .members(members).labels(labels).formerMembers(formerMembers).debutYear(2021).build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Test Agency")
                .members(members).labels(labels).formerMembers(formerMembers).debutYear(2020).disbandYear(2024).build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Test Agency")
                .members(members).labels(labels).formerMembers(formerMembers).debutYear(2020).disbandYear(2023)
                .subunits(Arrays.asList("Different")).build());
        assertThat(dto1).isNotEqualTo(GroupDTO.builder().groupId(groupId).groupName("Test Group").agency("Test Agency")
                .members(members).labels(labels).formerMembers(formerMembers).debutYear(2020).disbandYear(2023)
                .subunits(subunits).socialLinks(Arrays.asList("Different")).build());

        // Test with empty collections vs null collections
        GroupDTO dtoWithEmptyCollections = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Collections.emptyList())
                .labels(Collections.emptyList())
                .formerMembers(Collections.emptyList())
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Collections.emptyList())
                .socialLinks(Collections.emptyList())
                .build();

        GroupDTO dtoWithNullCollections = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .debutYear(2020)
                .disbandYear(2023)
                .build();

        assertThat(dtoWithEmptyCollections).isNotEqualTo(dtoWithNullCollections);
        assertThat(dtoWithNullCollections).isNotEqualTo(dtoWithEmptyCollections);
        assertThat(dtoWithEmptyCollections.hashCode()).isNotEqualTo(dtoWithNullCollections.hashCode());

        // Test with null fields in different combinations
        GroupDTO dtoWithSomeNulls1 = GroupDTO.builder()
                .groupId(groupId)
                .groupName(null)
                .agency("Test Agency")
                .members(members)
                .build();

        GroupDTO dtoWithSomeNulls2 = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency(null)
                .members(members)
                .build();

        assertThat(dtoWithSomeNulls1).isNotEqualTo(dtoWithSomeNulls2);
        assertThat(dtoWithSomeNulls1.hashCode()).isNotEqualTo(dtoWithSomeNulls2.hashCode());

        // Test with null ID
        GroupDTO dtoWithNullId = GroupDTO.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .build();

        GroupDTO anotherDtoWithNullId = GroupDTO.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .build();

        assertThat(dtoWithNullId).isNotEqualTo(dto1);
        assertThat(dto1).isNotEqualTo(dtoWithNullId);
        assertThat(dtoWithNullId).isEqualTo(anotherDtoWithNullId);
        assertThat(dtoWithNullId.hashCode()).isEqualTo(anotherDtoWithNullId.hashCode());

        // Test with different primitive values
        GroupDTO dtoWithDifferentPrimitive = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .debutYear(2021)  // Different primitive value
                .build();

        assertThat(dto1).isNotEqualTo(dtoWithDifferentPrimitive);
        assertThat(dtoWithDifferentPrimitive).isNotEqualTo(dto1);
        assertThat(dto1.hashCode()).isNotEqualTo(dtoWithDifferentPrimitive.hashCode());

        // Test with all null fields
        GroupDTO allNullsDto1 = GroupDTO.builder().build();
        GroupDTO allNullsDto2 = GroupDTO.builder().build();
        assertThat(allNullsDto1).isEqualTo(allNullsDto2);
        assertThat(allNullsDto1.hashCode()).isEqualTo(allNullsDto2.hashCode());

        // Test transitive property
        GroupDTO dtoA = GroupDTO.builder().groupId(groupId).groupName("Test").build();
        GroupDTO dtoB = GroupDTO.builder().groupId(groupId).groupName("Test").build();
        GroupDTO dtoC = GroupDTO.builder().groupId(groupId).groupName("Test").build();
        
        assertThat(dtoA).isEqualTo(dtoB);
        assertThat(dtoB).isEqualTo(dtoC);
        assertThat(dtoA).isEqualTo(dtoC);

        // Test with mutable lists
        List<String> mutableMembers = new ArrayList<>(members);
        GroupDTO dtoWithMutableList = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .members(mutableMembers)
                .build();

        GroupDTO dtoWithImmutableList = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .members(Collections.unmodifiableList(members))
                .build();

        assertThat(dtoWithMutableList).isEqualTo(dtoWithImmutableList);
        
        // Test with same content different list implementations
        GroupDTO dtoWithArrayList = GroupDTO.builder()
                .groupId(groupId)
                .members(new ArrayList<>(members))
                .build();
                
        assertThat(dtoWithArrayList).isEqualTo(GroupDTO.builder()
                .groupId(groupId)
                .members(members)
                .build());

        // Test with null vs empty string
        GroupDTO dtoWithNullString = GroupDTO.builder()
                .groupId(groupId)
                .groupName(null)
                .build();
                
        GroupDTO dtoWithEmptyString = GroupDTO.builder()
                .groupId(groupId)
                .groupName("")
                .build();
                
        assertThat(dtoWithNullString).isNotEqualTo(dtoWithEmptyString);

        // Test lists with same elements in different order
        List<String> membersReordered = Arrays.asList("Member2", "Member1");
        GroupDTO dtoWithReorderedList = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(membersReordered)
                .build();

        assertThat(dto1).isNotEqualTo(dtoWithReorderedList);

        // Test with one list null and others empty
        GroupDTO dtoWithMixedNullAndEmpty = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(null)
                .labels(Collections.emptyList())
                .formerMembers(Collections.emptyList())
                .subunits(Collections.emptyList())
                .socialLinks(Collections.emptyList())
                .build();

        GroupDTO dtoWithAllEmpty = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Collections.emptyList())
                .labels(Collections.emptyList())
                .formerMembers(Collections.emptyList())
                .subunits(Collections.emptyList())
                .socialLinks(Collections.emptyList())
                .build();

        assertThat(dtoWithMixedNullAndEmpty).isNotEqualTo(dtoWithAllEmpty);

        // Test null Integer vs 0
        GroupDTO dtoWithNullDisbandYear = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .debutYear(2020)
                .disbandYear(null)
                .build();

        GroupDTO dtoWithZeroDisbandYear = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .debutYear(2020)
                .disbandYear(0)
                .build();

        assertThat(dtoWithNullDisbandYear).isNotEqualTo(dtoWithZeroDisbandYear);

        // Test lists with null elements
        List<String> listWithNull = new ArrayList<>();
        listWithNull.add(null);
        
        GroupDTO dtoWithNullInList = GroupDTO.builder()
                .groupId(groupId)
                .members(listWithNull)
                .build();

        GroupDTO dtoWithEmptyList = GroupDTO.builder()
                .groupId(groupId)
                .members(Collections.emptyList())
                .build();

        assertThat(dtoWithNullInList).isNotEqualTo(dtoWithEmptyList);

        // Test all combinations of null fields
        GroupDTO allNullFields = GroupDTO.builder().build();
        GroupDTO justIdField = GroupDTO.builder().groupId(groupId).build();
        GroupDTO justNameField = GroupDTO.builder().groupName("Test").build();
        GroupDTO justAgencyField = GroupDTO.builder().agency("Test").build();
        GroupDTO justDebutYearField = GroupDTO.builder().debutYear(2020).build();
        GroupDTO justDisbandYearField = GroupDTO.builder().disbandYear(2023).build();

        assertThat(allNullFields).isNotEqualTo(justIdField);
        assertThat(allNullFields).isNotEqualTo(justNameField);
        assertThat(allNullFields).isNotEqualTo(justAgencyField);
        assertThat(allNullFields).isNotEqualTo(justDebutYearField);
        assertThat(allNullFields).isNotEqualTo(justDisbandYearField);

        // Test primitive debutYear edge cases
        GroupDTO dtoWithDefaultDebutYear = GroupDTO.builder()
                .groupId(groupId)
                .build();

        GroupDTO dtoWithZeroDebutYear = GroupDTO.builder()
                .groupId(groupId)
                .debutYear(0)
                .build();

        assertThat(dtoWithDefaultDebutYear).isEqualTo(dtoWithZeroDebutYear);

        // Test list content equality vs reference equality
        List<String> list1 = new ArrayList<>();
        list1.add("test");
        List<String> list2 = new ArrayList<>();
        list2.add("test");

        GroupDTO dtoWithList1 = GroupDTO.builder()
                .groupId(groupId)
                .members(list1)
                .build();

        GroupDTO dtoWithList2 = GroupDTO.builder()
                .groupId(groupId)
                .members(list2)
                .build();

        assertThat(dtoWithList1).isEqualTo(dtoWithList2);
        
        // Test lists with different case strings
        List<String> upperCaseList = Arrays.asList("TEST");
        List<String> lowerCaseList = Arrays.asList("test");

        GroupDTO dtoWithUpperCase = GroupDTO.builder()
                .groupId(groupId)
                .members(upperCaseList)
                .build();

        GroupDTO dtoWithLowerCase = GroupDTO.builder()
                .groupId(groupId)
                .members(lowerCaseList)
                .build();

        assertThat(dtoWithUpperCase).isNotEqualTo(dtoWithLowerCase);

        // Test self equality with null collections
        GroupDTO dtoWithAllNullCollections = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test")
                .members(null)
                .labels(null)
                .formerMembers(null)
                .subunits(null)
                .socialLinks(null)
                .build();
        assertThat(dtoWithAllNullCollections).isEqualTo(dtoWithAllNullCollections);
        assertThat(dtoWithAllNullCollections.hashCode()).isEqualTo(dtoWithAllNullCollections.hashCode());

        // Test mixed null and non-null collections
        GroupDTO dtoWithMixedCollections = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test")
                .members(Arrays.asList("Member1"))
                .labels(null)
                .formerMembers(Arrays.asList("Former1"))
                .subunits(null)
                .socialLinks(Arrays.asList("Link1"))
                .build();

        GroupDTO dtoWithAllNonNullCollections = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test")
                .members(Arrays.asList("Member1"))
                .labels(Collections.emptyList())
                .formerMembers(Arrays.asList("Former1"))
                .subunits(Collections.emptyList())
                .socialLinks(Arrays.asList("Link1"))
                .build();

        assertThat(dtoWithMixedCollections).isNotEqualTo(dtoWithAllNonNullCollections);
        assertThat(dtoWithMixedCollections.hashCode()).isNotEqualTo(dtoWithAllNonNullCollections.hashCode());

        // Test with special characters in strings
        GroupDTO dtoWithSpecialChars = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test\n\t\"Group")
                .agency("Test\r\nAgency")
                .build();

        GroupDTO dtoWithSpecialChars2 = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test\n\t\"Group")
                .agency("Test\r\nAgency")
                .build();

        assertThat(dtoWithSpecialChars).isEqualTo(dtoWithSpecialChars2);
        assertThat(dtoWithSpecialChars.hashCode()).isEqualTo(dtoWithSpecialChars2.hashCode());

        // Test all null fields
        GroupDTO allNullDto = GroupDTO.builder().build();
        assertThat(allNullDto).isEqualTo(allNullDto);
        assertThat(allNullDto.hashCode()).isEqualTo(allNullDto.hashCode());

        // Test null vs empty/default values
        GroupDTO allEmptyDto = GroupDTO.builder()
                .groupId(null)
                .groupName("")
                .agency("")
                .members(Collections.emptyList())
                .labels(Collections.emptyList())
                .formerMembers(Collections.emptyList())
                .debutYear(0)
                .disbandYear(null)
                .subunits(Collections.emptyList())
                .socialLinks(Collections.emptyList())
                .build();
        assertThat(allNullDto).isNotEqualTo(allEmptyDto);

        // Test different groupId but identical content
        UUID differentId = UUID.randomUUID();
        GroupDTO dtoWithDifferentId = GroupDTO.builder()
                .groupId(differentId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .labels(labels)
                .formerMembers(formerMembers)
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(subunits)
                .socialLinks(socialLinks)
                .build();
        assertThat(dto1).isNotEqualTo(dtoWithDifferentId);

        // Test empty lists vs null lists
        GroupDTO dtoWithEmptyLists = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Collections.emptyList())
                .labels(Collections.emptyList())
                .formerMembers(Collections.emptyList())
                .subunits(Collections.emptyList())
                .socialLinks(Collections.emptyList())
                .build();

        GroupDTO dtoWithNullLists = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(null)
                .labels(null)
                .formerMembers(null)
                .subunits(null)
                .socialLinks(null)
                .build();

        assertThat(dtoWithEmptyLists).isNotEqualTo(dtoWithNullLists);

        // Test same list instances
        List<String> sharedList = Arrays.asList("Member1");
        GroupDTO dto3 = GroupDTO.builder()
                .groupId(groupId)
                .members(sharedList)
                .build();

        GroupDTO dto4 = GroupDTO.builder()
                .groupId(groupId)
                .members(sharedList)
                .build();

        assertThat(dto3).isEqualTo(dto4);

        // Test one null field at a time
        GroupDTO dtoWithNullGroupName = GroupDTO.builder()
                .groupId(groupId)
                .groupName(null)
                .agency("Test Agency")
                .build();

        GroupDTO dtoWithNullAgency = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency(null)
                .build();

        assertThat(dtoWithNullGroupName).isNotEqualTo(dtoWithNullAgency);
        assertThat(dtoWithNullGroupName).isNotEqualTo(dto1);
        assertThat(dtoWithNullAgency).isNotEqualTo(dto1);

        // Test with zero vs null for primitive wrappers
        GroupDTO anotherDtoWithZeroDisbandYear = GroupDTO.builder()
                .groupId(groupId)
                .disbandYear(0)
                .build();

        GroupDTO anotherDtoWithNullDisbandYear = GroupDTO.builder()
                .groupId(groupId)
                .disbandYear(null)
                .build();

        assertThat(anotherDtoWithZeroDisbandYear).isNotEqualTo(anotherDtoWithNullDisbandYear);
    }

    @Test
    void testCanEqual() {
        GroupDTO dto = GroupDTO.builder()
                .groupId(UUID.randomUUID())
                .groupName("Test Group")
                .build();

        // Test with same type
        GroupDTO another = GroupDTO.builder()
                .groupId(UUID.randomUUID())
                .groupName("Another Group")
                .build();
        assertThat(dto.canEqual(another)).isTrue();

        // Test with different type
        assertThat(dto.canEqual(new Object())).isFalse();
        assertThat(dto.canEqual(null)).isFalse();

        // Create a class that extends GroupDTO to test inheritance
        class ExtendedGroupDTO extends GroupDTO {
            public ExtendedGroupDTO() {
                super(UUID.randomUUID(), "Extended", "Agency", 
                      Collections.emptyList(), Collections.emptyList(), 
                      Collections.emptyList(), 2020, null,
                      Collections.emptyList(), Collections.emptyList());
            }
        }
        ExtendedGroupDTO extendedDto = new ExtendedGroupDTO();
        assertThat(dto.canEqual(extendedDto)).isTrue();
        assertThat(extendedDto.canEqual(dto)).isTrue();
    }

    @Test
    void testToString() {
        GroupDTO dto = GroupDTO.builder()
                .groupId(UUID.randomUUID())
                .groupName("Test Group")
                .agency("Test Agency")
                .build();

        String toString = dto.toString();
        assertThat(toString)
                .contains("groupId")
                .contains("groupName")
                .contains("agency")
                .contains("Test Group")
                .contains("Test Agency");
    }

    @Test
    void testBuilderToString() {
        GroupDTO.GroupDTOBuilder builder = GroupDTO.builder()
                .groupId(UUID.randomUUID())
                .groupName("Test Group")
                .agency("Test Agency");

        String builderString = builder.toString();
        assertThat(builderString)
                .contains("groupId")
                .contains("groupName")
                .contains("agency")
                .contains("Test Group")
                .contains("Test Agency");
    }

    @Test
    void testSetters() {
        UUID groupId = UUID.randomUUID();
        List<String> members = Arrays.asList("Member1", "Member2");
        List<String> labels = Arrays.asList("Label1", "Label2");
        List<String> formerMembers = Arrays.asList("Former1");
        List<String> subunits = Arrays.asList("Subunit1");
        List<String> socialLinks = Arrays.asList("link1");

        GroupDTO dto = GroupDTO.builder()
                .groupId(groupId)
                .build();

        // Test setGroupId specifically
        UUID newGroupId = UUID.randomUUID();
        dto.setGroupId(newGroupId);
        assertThat(dto.getGroupId()).isEqualTo(newGroupId);

        dto.setGroupName("Test Group");
        dto.setAgency("Test Agency");
        dto.setMembers(members);
        dto.setLabels(labels);
        dto.setFormerMembers(formerMembers);
        dto.setDebutYear(2020);
        dto.setDisbandYear(2023);
        dto.setSubunits(subunits);
        dto.setSocialLinks(socialLinks);

        assertThat(dto.getGroupId()).isEqualTo(newGroupId);  // Verify ID hasn't changed
        assertThat(dto.getGroupName()).isEqualTo("Test Group");
        assertThat(dto.getAgency()).isEqualTo("Test Agency");
        assertThat(dto.getMembers()).isEqualTo(members);
        assertThat(dto.getLabels()).isEqualTo(labels);
        assertThat(dto.getFormerMembers()).isEqualTo(formerMembers);
        assertThat(dto.getDebutYear()).isEqualTo(2020);
        assertThat(dto.getDisbandYear()).isEqualTo(2023);
        assertThat(dto.getSubunits()).isEqualTo(subunits);
        assertThat(dto.getSocialLinks()).isEqualTo(socialLinks);
    }

    @Test
    void testBuilder() {
        UUID groupId = UUID.randomUUID();
        List<String> members = Arrays.asList("Member1", "Member2");
        List<String> labels = Arrays.asList("Label1", "Label2");
        
        GroupDTO dto = GroupDTO.builder()
                .groupId(groupId)
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .labels(labels)
                .debutYear(2020)
                .build();

        assertThat(dto.getGroupId()).isEqualTo(groupId);
        assertThat(dto.getGroupName()).isEqualTo("Test Group");
        assertThat(dto.getAgency()).isEqualTo("Test Agency");
        assertThat(dto.getMembers()).isEqualTo(members);
        assertThat(dto.getLabels()).isEqualTo(labels);
        assertThat(dto.getDebutYear()).isEqualTo(2020);
    }
}
