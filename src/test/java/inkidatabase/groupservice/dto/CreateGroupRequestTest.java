package inkidatabase.groupservice.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateGroupRequestTest {

    @Test
    void testBuilderAndToString() {
        List<String> members = Arrays.asList("Member1", "Member2");
        List<String> labels = Arrays.asList("Label1", "Label2");
        List<String> formerMembers = Arrays.asList("Former1");
        List<String> subunits = Arrays.asList("Subunit1");
        List<String> socialLinks = Arrays.asList("link1");

        CreateGroupRequest.CreateGroupRequestBuilder builder = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .labels(labels)
                .formerMembers(formerMembers)
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(subunits)
                .socialLinks(socialLinks);

        // Test builder toString()
        String builderString = builder.toString();
        assertThat(builderString)
                .contains("groupName")
                .contains("agency")
                .contains("members")
                .contains("Test Group")
                .contains("Test Agency");

        // Test build and toString()
        CreateGroupRequest request = builder.build();
        String toString = request.toString();
        assertThat(toString)
                .contains("groupName")
                .contains("agency")
                .contains("members")
                .contains("Test Group")
                .contains("Test Agency");
    }

    @Test
    void testSettersAndGetters() {
        CreateGroupRequest request = CreateGroupRequest.builder().build();
        
        request.setGroupName("Test Group");
        request.setAgency("Test Agency");
        request.setMembers(Arrays.asList("Member1", "Member2"));
        request.setLabels(Arrays.asList("Label1", "Label2"));
        request.setFormerMembers(Arrays.asList("Former1"));
        request.setDebutYear(2020);
        request.setDisbandYear(2023);
        request.setSubunits(Arrays.asList("Subunit1"));
        request.setSocialLinks(Arrays.asList("link1"));

        assertThat(request.getGroupName()).isEqualTo("Test Group");
        assertThat(request.getAgency()).isEqualTo("Test Agency");
        assertThat(request.getMembers()).containsExactly("Member1", "Member2");
        assertThat(request.getLabels()).containsExactly("Label1", "Label2");
        assertThat(request.getFormerMembers()).containsExactly("Former1");
        assertThat(request.getDebutYear()).isEqualTo(2020);
        assertThat(request.getDisbandYear()).isEqualTo(2023);
        assertThat(request.getSubunits()).containsExactly("Subunit1");
        assertThat(request.getSocialLinks()).containsExactly("link1");
    }

    @Test
    void testEqualsAndHashCode() {
        List<String> members = Arrays.asList("Member1", "Member2");
        List<String> labels = Arrays.asList("Label1", "Label2");
        List<String> formerMembers = Arrays.asList("Former1");
        List<String> subunits = Arrays.asList("Subunit1");
        List<String> socialLinks = Arrays.asList("link1");

        CreateGroupRequest request1 = CreateGroupRequest.builder()
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

        CreateGroupRequest request2 = CreateGroupRequest.builder()
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

        CreateGroupRequest request3 = CreateGroupRequest.builder()
                .groupName("Different Group")
                .agency("Test Agency")
                .members(members)
                .build();

        // Test equality
        assertThat(request1).isEqualTo(request1); // same instance
        assertThat(request1).isEqualTo(request2); // equivalent instance
        assertThat(request1).isNotEqualTo(request3); // different values
        assertThat(request1).isNotEqualTo(null); // null check
        assertThat(request1).isNotEqualTo(new Object()); // different type

        // Test hashCode
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request3.hashCode());

        // Test with null fields
        CreateGroupRequest requestWithNullGroupName = CreateGroupRequest.builder()
                .groupName(null)
                .agency("Test Agency")
                .members(members)
                .build();

        CreateGroupRequest requestWithNullAgency = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency(null)
                .members(members)
                .build();

        CreateGroupRequest requestWithNullMembers = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(null)
                .build();

        assertThat(requestWithNullGroupName).isNotEqualTo(request1);
        assertThat(requestWithNullAgency).isNotEqualTo(request1);
        assertThat(requestWithNullMembers).isNotEqualTo(request1);
        assertThat(requestWithNullGroupName).isNotEqualTo(requestWithNullAgency);
        assertThat(requestWithNullGroupName.hashCode()).isNotEqualTo(requestWithNullAgency.hashCode());

        // Test with empty collections vs null collections
        CreateGroupRequest requestWithEmptyCollections = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Collections.emptyList())
                .labels(Collections.emptyList())
                .formerMembers(Collections.emptyList())
                .subunits(Collections.emptyList())
                .socialLinks(Collections.emptyList())
                .build();

        CreateGroupRequest requestWithNullCollections = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Collections.emptyList())
                .labels(null)
                .formerMembers(null)
                .subunits(null)
                .socialLinks(null)
                .build();

        assertThat(requestWithEmptyCollections).isNotEqualTo(requestWithNullCollections);
        assertThat(requestWithEmptyCollections.hashCode()).isNotEqualTo(requestWithNullCollections.hashCode());

        // Test with zero vs null for primitive wrappers
        CreateGroupRequest requestWithZeroYear = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .debutYear(0)
                .disbandYear(0)
                .build();

        CreateGroupRequest requestWithNullYear = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(members)
                .debutYear(null)
                .disbandYear(null)
                .build();

        assertThat(requestWithZeroYear).isNotEqualTo(requestWithNullYear);
        assertThat(requestWithZeroYear.hashCode()).isNotEqualTo(requestWithNullYear.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithNullAndEmptyFields() {
        // Test with all null optional fields
        CreateGroupRequest allNullOptionals = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1"))
                .labels(null)
                .formerMembers(null)
                .debutYear(null)
                .disbandYear(null)
                .subunits(null)
                .socialLinks(null)
                .build();

        CreateGroupRequest allEmptyOptionals = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1"))
                .labels(Collections.emptyList())
                .formerMembers(Collections.emptyList())
                .debutYear(0)
                .disbandYear(0)
                .subunits(Collections.emptyList())
                .socialLinks(Collections.emptyList())
                .build();

        // Test equality with itself
        assertThat(allNullOptionals).isEqualTo(allNullOptionals);
        assertThat(allEmptyOptionals).isEqualTo(allEmptyOptionals);
        
        // Test inequality between null and empty
        assertThat(allNullOptionals).isNotEqualTo(allEmptyOptionals);
        
        // Test hashCode with null fields
        assertThat(allNullOptionals.hashCode()).isNotEqualTo(allEmptyOptionals.hashCode());

        // Test with different combinations of null fields
        CreateGroupRequest someNullFields = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(null)
                .debutYear(2020)
                .disbandYear(null)
                .subunits(Arrays.asList("Subunit1"))
                .socialLinks(null)
                .build();

        CreateGroupRequest differentNullFields = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1"))
                .labels(null)
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(null)
                .socialLinks(Arrays.asList("link1"))
                .build();

        // Test inequality with different null field combinations
        assertThat(someNullFields).isNotEqualTo(differentNullFields);
        assertThat(someNullFields.hashCode()).isNotEqualTo(differentNullFields.hashCode());

        // Test with same values but different list instances
        CreateGroupRequest request1 = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(new java.util.ArrayList<>(Arrays.asList("Member1")))
                .labels(new java.util.ArrayList<>(Arrays.asList("Label1")))
                .build();

        CreateGroupRequest request2 = CreateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(new java.util.ArrayList<>(Arrays.asList("Member1")))
                .labels(new java.util.ArrayList<>(Arrays.asList("Label1")))
                .build();

        // Should be equal despite being different list instances
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
    }
}
