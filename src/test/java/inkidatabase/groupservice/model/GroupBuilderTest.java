package inkidatabase.groupservice.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import enums.GroupActiveStatus;

import static org.assertj.core.api.Assertions.assertThat;

class GroupBuilderTest {

    @Test
    void builderShouldSetAllFields() {
        // Given
        String groupName = "Test Group";
        String agency = "Test Agency";
        int debutYear = 2020;
        List<String> labels = Arrays.asList("Label1", "Label2");
        List<String> members = Arrays.asList("Member1", "Member2");
        List<String> formerMembers = Arrays.asList("Former1", "Former2");
        int disbandYear = 2023;
        List<String> subunits = Arrays.asList("Unit1", "Unit2");
        List<String> socialLinks = Arrays.asList("Link1", "Link2");

        // When
        Group group = Group.builder(groupName, agency, debutYear)
                .labels(labels)
                .members(members)
                .formerMembers(formerMembers)
                .disbandYear(disbandYear)
                .subunits(subunits)
                .socialLinks(socialLinks)
                .build();

        // Then
        assertThat(group.getGroupName()).isEqualTo(groupName);
        assertThat(group.getAgency()).isEqualTo(agency);
        assertThat(group.getDebutYear()).isEqualTo(debutYear);
        assertThat(group.getLabels()).containsExactlyElementsOf(labels);
        assertThat(group.getMembers()).containsExactlyElementsOf(members);
        assertThat(group.getFormerMembers()).containsExactlyElementsOf(formerMembers);
        assertThat(group.getDisbandYear()).isEqualTo(disbandYear);
        assertThat(group.getSubunits()).containsExactlyElementsOf(subunits);
        assertThat(group.getSocialLinks()).containsExactlyElementsOf(socialLinks);
    }

    @Test
    void builderShouldHandleNullCollections() {
        // Given
        String groupName = "Test Group";
        String agency = "Test Agency";
        int debutYear = 2020;

        // When
        Group group = Group.builder(groupName, agency, debutYear)
                .labels(null)
                .members(null)
                .formerMembers(null)
                .subunits(null)
                .socialLinks(null)
                .build();

        // Then
        assertThat(group.getLabels()).isEmpty();
        assertThat(group.getMembers()).isEmpty();
        assertThat(group.getFormerMembers()).isEmpty();
        assertThat(group.getSubunits()).isEmpty();
        assertThat(group.getSocialLinks()).isEmpty();
    }

    @Test
    void toStringShouldIncludeAllFields() {
        // Given
        String groupName = "Test Group";
        String agency = "Test Agency";
        int debutYear = 2020;
        List<String> labels = Arrays.asList("Label1");
        List<String> members = Arrays.asList("Member1");

        Group group = Group.builder(groupName, agency, debutYear)
                .labels(labels)
                .members(members)
                .build();

        // When
        String result = group.toString();

        // Then
        assertThat(result)
                .contains("groupName=" + groupName)
                .contains("agency=" + agency)
                .contains("debutYear=" + debutYear)
                .contains("labels=" + labels)
                .contains("members=" + members);
    }

    @Test
    void builderToStringShouldIncludeAllFields() {
        // Given
        String groupName = "Test Group";
        String agency = "Test Agency";
        int debutYear = 2020;
        List<String> labels = Arrays.asList("Label1");
        List<String> members = Arrays.asList("Member1");

        // When
        Group.GroupBuilder builder = Group.builder(groupName, agency, debutYear)
                .labels(labels)
                .members(members);
        String result = builder.toString();

        // Then
        assertThat(result)
                .contains("groupName=" + groupName)
                .contains("agency=" + agency)
                .contains("debutYear=" + debutYear)
                .contains("labels=" + labels)
                .contains("members=" + members);
    }

    @Test
    void addFormerMemberShouldAddToList() {
        // Given
        Group group = Group.builder("Test Group", "Test Agency", 2020).build();
        String formerMember = "Former Member 1";

        // When
        group.addFormerMember(formerMember);

        // Then
        assertThat(group.getFormerMembers())
                .hasSize(1)
                .contains(formerMember);
    }

    @Test
    void addSubunitShouldAddToList() {
        // Given
        Group group = Group.builder("Test Group", "Test Agency", 2020).build();
        String subunit = "Subunit 1";

        // When
        group.addSubunit(subunit);

        // Then
        assertThat(group.getSubunits())
                .hasSize(1)
                .contains(subunit);
    }

    @Test
    void addSocialLinkShouldAddToList() {
        // Given
        Group group = Group.builder("Test Group", "Test Agency", 2020).build();
        String socialLink = "https://example.com/social";

        // When
        group.addSocialLink(socialLink);

        // Then
        assertThat(group.getSocialLinks())
                .hasSize(1)
                .contains(socialLink);
    }

    @Test
    void updateStatusShouldSetActiveWhenNotDisbanded() {
        // Given
        Group group = Group.builder("Test Group", "Test Agency", 2020)
                .members(Arrays.asList("Member1"))
                .build();

        // When
        group.updateStatus();

        // Then
        assertThat(group.getStatus()).isEqualTo(GroupActiveStatus.ACTIVE);
    }

    @Test
    void updateStatusShouldSetDisbandedWhenDisbandYearIsSet() {
        // Given
        Group group = Group.builder("Test Group", "Test Agency", 2020)
                .disbandYear(2023)
                .build();

        // When
        group.updateStatus();

        // Then
        assertThat(group.getStatus()).isEqualTo(GroupActiveStatus.DISBANDED);
    }

    @Test
    void updateStatusShouldSetInactiveWhenNoMembers() {
        // Given
        Group group = Group.builder("Test Group", "Test Agency", 2020).build();

        // When
        group.updateStatus();

        // Then
        assertThat(group.getStatus()).isEqualTo(GroupActiveStatus.INACTIVE);
    }

    @Test
    void updateStatusShouldSetInactiveWhenMembersIsNull() {
        // Given
        Group group = Group.builder("Test Group", "Test Agency", 2020).build();
        group.setMembers(null);  // Force members to be null to test this branch

        // When
        group.updateStatus();

        // Then
        assertThat(group.getStatus()).isEqualTo(GroupActiveStatus.INACTIVE);
    }
}
