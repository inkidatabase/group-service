package inkidatabase.groupservice.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateGroupRequestTest {

    @Test
    void testBuilderAndToString() {
        List<String> members = Arrays.asList("Member1", "Member2");
        List<String> labels = Arrays.asList("Label1", "Label2");
        List<String> formerMembers = Arrays.asList("Former1");
        List<String> subunits = Arrays.asList("Subunit1");
        List<String> socialLinks = Arrays.asList("link1");

        UpdateGroupRequest.UpdateGroupRequestBuilder builder = UpdateGroupRequest.builder()
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
        UpdateGroupRequest request = builder.build();
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
        UpdateGroupRequest request = UpdateGroupRequest.builder().build();
        
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
        // Create two identical requests
        UpdateGroupRequest request1 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request2 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        // Create requests with different values for each field
        UpdateGroupRequest request3 = UpdateGroupRequest.builder()
                .groupName("Different Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request4 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Different Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request5 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Different", "Members"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request6 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Different"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request7 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Different"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request8 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2021)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request9 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2024)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request10 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Different"))
                .socialLinks(Arrays.asList("Link1"))
                .build();

        UpdateGroupRequest request11 = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Different"))
                .build();

        // Test reflexivity
        assertThat(request1).isEqualTo(request1);

        // Test symmetry
        assertThat(request1).isEqualTo(request2);
        assertThat(request2).isEqualTo(request1);

        // Test transitivity
        assertThat(request1).isEqualTo(request2);
        UpdateGroupRequest request2Copy = UpdateGroupRequest.builder()
                .groupName("Test Group")
                .agency("Test Agency")
                .members(Arrays.asList("Member1", "Member2"))
                .labels(Arrays.asList("Label1"))
                .formerMembers(Arrays.asList("Former1"))
                .debutYear(2020)
                .disbandYear(2023)
                .subunits(Arrays.asList("Sub1"))
                .socialLinks(Arrays.asList("Link1"))
                .build();
        assertThat(request2).isEqualTo(request2Copy);
        assertThat(request1).isEqualTo(request2Copy);

        // Test null and different type
        assertThat(request1).isNotEqualTo(null);
        assertThat(request1).isNotEqualTo(new Object());

        // Test different field values
        assertThat(request1).isNotEqualTo(request3);  // different group name
        assertThat(request1).isNotEqualTo(request4);  // different agency
        assertThat(request1).isNotEqualTo(request5);  // different members
        assertThat(request1).isNotEqualTo(request6);  // different labels
        assertThat(request1).isNotEqualTo(request7);  // different former members
        assertThat(request1).isNotEqualTo(request8);  // different debut year
        assertThat(request1).isNotEqualTo(request9);  // different disband year
        assertThat(request1).isNotEqualTo(request10); // different subunits
        assertThat(request1).isNotEqualTo(request11); // different social links

        // Test hashCode
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request3.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request4.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request5.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request6.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request7.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request8.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request9.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request10.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request11.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithNullAndEmptyFields() {
        UpdateGroupRequest request1 = UpdateGroupRequest.builder()
                .groupName(null)
                .agency(null)
                .members(null)
                .labels(null)
                .formerMembers(null)
                .debutYear(null)
                .disbandYear(null)
                .subunits(null)
                .socialLinks(null)
                .build();

        UpdateGroupRequest request2 = UpdateGroupRequest.builder()
                .groupName(null)
                .agency(null)
                .members(null)
                .labels(null)
                .formerMembers(null)
                .debutYear(null)
                .disbandYear(null)
                .subunits(null)
                .socialLinks(null)
                .build();

        UpdateGroupRequest request3 = UpdateGroupRequest.builder()
                .groupName("")
                .agency("")
                .members(Arrays.asList())
                .labels(Arrays.asList())
                .formerMembers(Arrays.asList())
                .debutYear(null)
                .disbandYear(null)
                .subunits(Arrays.asList())
                .socialLinks(Arrays.asList())
                .build();

        // Test equality with null fields
        assertThat(request1).isEqualTo(request2);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());

        // Test equality between null and empty fields
        assertThat(request1).isNotEqualTo(request3);
        assertThat(request1.hashCode()).isNotEqualTo(request3.hashCode());

        // Test canEqual
        assertThat(request1.canEqual(request2)).isTrue();
        assertThat(request1.canEqual(new Object())).isFalse();
    }

    @Test
    void testDebutYearValues() {
        // Test valid debutYear
        UpdateGroupRequest validRequest = UpdateGroupRequest.builder()
                .debutYear(2000)
                .build();
        assertThat(validRequest.getDebutYear()).isEqualTo(2000);

        // Test setting invalid debutYear (before 1900)
        UpdateGroupRequest invalidRequest = UpdateGroupRequest.builder()
                .debutYear(1899)
                .build();
        assertThat(invalidRequest.getDebutYear()).isEqualTo(1899);

        // Test null debutYear
        UpdateGroupRequest nullDebutYearRequest = UpdateGroupRequest.builder()
                .debutYear(null)
                .build();
        assertThat(nullDebutYearRequest.getDebutYear()).isNull();
    }
}
