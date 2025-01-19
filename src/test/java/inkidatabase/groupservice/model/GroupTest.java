package inkidatabase.groupservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import enums.GroupActiveStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    private Group testGroup;
    private final String groupName = "BTS";
    private final String agency = "HYBE";
    private final int debutYear = 2013;

    @BeforeEach
    void setUp() {
        testGroup = new Group(groupName, agency, debutYear);
        testGroup.setGroupId(UUID.randomUUID());
    }

    @Test
    void testSimpleConstructor() {
        assertNotNull(testGroup);
        assertEquals(groupName, testGroup.getGroupName());
        assertEquals(agency, testGroup.getAgency());
        assertEquals(debutYear, testGroup.getDebutYear());
    }

    @Test
    void testGetGroupId() {
        assertNotNull(testGroup.getGroupId());
    }

    @Test
    void testGetGroupName() {
        assertEquals(groupName, testGroup.getGroupName());
    }

    @Test
    void testGetAgency() {
        assertEquals(agency, testGroup.getAgency());
    }

    @Test
    void testGetDebutYear() {
        assertEquals(debutYear, testGroup.getDebutYear());
    }

    @Test
    void testGetDisbandYear() {
        assertEquals(0, testGroup.getDisbandYear());
        
        testGroup.setDisbandYear(2016);
        assertEquals(2016, testGroup.getDisbandYear());
    }

    @Test
    void testSetAndGetMembers() {
        List<String> members = Arrays.asList("RM", "Jin", "Suga");
        testGroup.setMembers(members);
        
        assertEquals(members, testGroup.getMembers());
    }

    @Test
    void testAddMember() {
        testGroup.setMembers(Arrays.asList("RM", "Jin"));
        testGroup.addMember("Suga");
        
        assertTrue(testGroup.getMembers().contains("Suga"));
        assertEquals(3, testGroup.getMembers().size());
    }

    @Test
    void testSetAndGetLabels() {
        List<String> labels = Arrays.asList("kpop", "boy-group");
        testGroup.setLabels(labels);
        
        assertEquals(labels, testGroup.getLabels());
    }

    @Test
    void testAddLabel() {
        testGroup.setLabels(Arrays.asList("kpop"));
        testGroup.addLabel("boy-group");
        
        assertTrue(testGroup.getLabels().contains("boy-group"));
        assertEquals(2, testGroup.getLabels().size());
    }

    @Test
    void testDisbandedGroup() {
        assertEquals(GroupActiveStatus.INACTIVE, testGroup.getStatus());
        
        testGroup.setDisbandYear(2016);
        assertEquals(GroupActiveStatus.DISBANDED, testGroup.getStatus());
    }

    @Test
    void testGroupActiveStatusTransitions() {
        // New group should be inactive
        assertEquals(GroupActiveStatus.INACTIVE, testGroup.getStatus());
        
        // Add members to make it active
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga"));
        assertEquals(GroupActiveStatus.ACTIVE, testGroup.getStatus());
        
        // Disband the group
        testGroup.setDisbandYear(2016);
        assertEquals(GroupActiveStatus.DISBANDED, testGroup.getStatus());
    }

    @Test
    void testGroupActiveStatusWithNullMembers() {
        testGroup.setMembers(new java.util.ArrayList<>());  
        assertEquals(GroupActiveStatus.INACTIVE, testGroup.getStatus());
    }

    @Test
    void testGroupActiveStatusInactive() {
        testGroup.setMembers(Collections.emptyList());
        assertEquals(GroupActiveStatus.INACTIVE, testGroup.getStatus());
    }

    @Test
    void testGroupActiveStatusWithNullMembersReference() {
        // Use reflection to set members to null since there's no public API to do this
        try {
            java.lang.reflect.Field membersField = Group.class.getDeclaredField("members");
            membersField.setAccessible(true);
            membersField.set(testGroup, null);
            
            // Force status update
            testGroup.updateStatus();
            
            assertEquals(GroupActiveStatus.INACTIVE, testGroup.getStatus());
        } catch (Exception e) {
            fail("Failed to set members field to null: " + e.getMessage());
        }
    }

    @Test
    void testCollectionImmutability() {
        List<String> members = Arrays.asList("RM", "Jin", "Suga");
        testGroup.setMembers(members);
        
        List<String> retrievedMembers = testGroup.getMembers();
        assertThrows(UnsupportedOperationException.class, () -> retrievedMembers.add("V"));
    }

    @Test
    void testDefensiveCopying() {
        List<String> members = Arrays.asList("RM", "Jin", "Suga");
        testGroup.setMembers(members);
        
        List<String> retrievedMembers = testGroup.getMembers();
        assertNotSame(members, retrievedMembers);
    }

    @Test
    void testSecondaryConstructorWithNonNullValues() {
        List<String> labels = Arrays.asList("kpop", "boy-group");
        List<String> members = Arrays.asList("RM", "Jin", "Suga");
        
        Group group = new Group(groupName, agency, debutYear, labels, members);
        
        assertEquals(groupName, group.getGroupName());
        assertEquals(agency, group.getAgency());
        assertEquals(debutYear, group.getDebutYear());
        assertEquals(labels, group.getLabels());
        assertEquals(members, group.getMembers());
        assertEquals(GroupActiveStatus.ACTIVE, group.getStatus());
    }

    @Test
    void testSecondaryConstructorWithNullValues() {
        Group group = new Group(groupName, agency, debutYear, null, null);
        
        assertEquals(groupName, group.getGroupName());
        assertEquals(agency, group.getAgency());
        assertEquals(debutYear, group.getDebutYear());
        assertTrue(group.getLabels().isEmpty());
        assertTrue(group.getMembers().isEmpty());
        assertEquals(GroupActiveStatus.INACTIVE, group.getStatus());
    }

    @Test
    void testSecondaryConstructorWithMixedNullValues() {
        List<String> labels = Arrays.asList("kpop", "boy-group");
        
        Group group = new Group(groupName, agency, debutYear, labels, null);
        
        assertEquals(groupName, group.getGroupName());
        assertEquals(agency, group.getAgency());
        assertEquals(debutYear, group.getDebutYear());
        assertEquals(labels, group.getLabels());
        assertTrue(group.getMembers().isEmpty());
        assertEquals(GroupActiveStatus.INACTIVE, group.getStatus());
    }

    @Test
    void testDisbandedStatusTakesPrecedenceOverActive() {
        // Given a group with members (which would normally make it ACTIVE)
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga"));
        assertEquals(GroupActiveStatus.ACTIVE, testGroup.getStatus());
        
        // When the group is disbanded
        testGroup.setDisbandYear(2024);
        
        // Then disbanded status should take precedence
        assertEquals(GroupActiveStatus.DISBANDED, testGroup.getStatus());
        
        // And it should stay disbanded even if more members are added
        testGroup.addMember("V");
        assertEquals(GroupActiveStatus.DISBANDED, testGroup.getStatus());
    }

    @Test
    void testNegativeDisbandYearDoesNotAffectStatus() {
        // Given a group with members
        testGroup.setMembers(Arrays.asList("RM", "Jin", "Suga"));
        assertEquals(GroupActiveStatus.ACTIVE, testGroup.getStatus());
        
        // When setting a negative disband year
        testGroup.setDisbandYear(-2024);
        
        // Then the group should remain active since disbandYear <= 0
        assertEquals(GroupActiveStatus.ACTIVE, testGroup.getStatus());
    }
}
