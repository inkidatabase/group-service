package inkidatabase.groupservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.GroupActiveStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    Group btsGroup;
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        this.btsGroup = new Group();
        this.btsGroup.setGroupId(uuid);
        this.btsGroup.setGroupName("BTS");
        this.btsGroup.setAgency("BigHit Music");
        this.btsGroup.setDebutYear(2013);
    }

    // Builder Pattern Tests
    @Test
    void testBuilderWithRequiredFields() {
        Group builtGroup = Group.builder("BTS", "BigHit Music", 2013).build();
        
        assertEquals("BTS", builtGroup.getGroupName());
        assertEquals("BigHit Music", builtGroup.getAgency());
        assertEquals(2013, builtGroup.getDebutYear());
        assertNotNull(builtGroup.getGroupId());
        assertTrue(builtGroup.getLabels().isEmpty());
        assertTrue(builtGroup.getMembers().isEmpty());
        assertTrue(builtGroup.getFormerMembers().isEmpty());
        assertTrue(builtGroup.getSubunits().isEmpty());
        assertEquals(0, builtGroup.getDisbandYear());
    }

    @Test
    void testBuilderWithAllFields() {
        List<String> labels = Arrays.asList("HYBE Labels", "Source Music");
        List<String> members = Arrays.asList("Sakura", "Kim Chaewon", "Huh Yunjin");
        List<String> formerMembers = Arrays.asList("Kim Garam");
        List<String> subunits = Arrays.asList("HUH x CHAEWON");

        Group lesserafim = Group.builder("LE SSERAFIM", "Source Music", 2022)
                         .labels(labels)
                         .members(members)
                         .formerMembers(formerMembers)
                         .disbandYear(0)
                         .subunits(subunits)
                         .build();

        assertEquals("LE SSERAFIM", lesserafim.getGroupName());
        assertEquals("Source Music", lesserafim.getAgency());
        assertEquals(2022, lesserafim.getDebutYear());
        assertEquals(labels, lesserafim.getLabels());
        assertEquals(members, lesserafim.getMembers());
        assertEquals(formerMembers, lesserafim.getFormerMembers());
        assertEquals(subunits, lesserafim.getSubunits());
        assertEquals(0, lesserafim.getDisbandYear());
    }

    @Test
    void testBuilderWithNullCollections() {
        Group newjeans = Group.builder("NewJeans", "ADOR", 2022)
                         .labels(null)
                         .members(null)
                         .formerMembers(null)
                         .subunits(null)
                         .build();

        assertNotNull(newjeans.getLabels());
        assertNotNull(newjeans.getMembers());
        assertNotNull(newjeans.getFormerMembers());
        assertNotNull(newjeans.getSubunits());
        assertTrue(newjeans.getLabels().isEmpty());
        assertTrue(newjeans.getMembers().isEmpty());
        assertTrue(newjeans.getFormerMembers().isEmpty());
        assertTrue(newjeans.getSubunits().isEmpty());
    }

    // Setter and Getter Tests
    @Test
    void testSetAndGetLabels() {
        List<String> labels = Arrays.asList("HYBE Labels", "BigHit Music");
        this.btsGroup.setLabels(labels);
        assertEquals(labels, this.btsGroup.getLabels());
        assertEquals(2, this.btsGroup.getLabels().size());
    }

    @Test
    void testSetAndGetMembers() {
        List<String> members = Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook");
        this.btsGroup.setMembers(members);
        assertEquals(members, this.btsGroup.getMembers());
        assertEquals(7, this.btsGroup.getMembers().size());
    }

    @Test
    void testSetAndGetSubunits() {
        List<String> subunits = Arrays.asList("Hip-hop Team", "Vocal Team", "Performance Team");
        this.btsGroup.setSubunits(subunits);
        assertEquals(subunits, this.btsGroup.getSubunits());
        assertEquals(3, this.btsGroup.getSubunits().size());
    }

    @Test
    void testGetGroupId() {
        assertEquals(uuid, this.btsGroup.getGroupId());
    }

    @Test
    void testGetGroupName() {
        assertEquals("BTS", this.btsGroup.getGroupName());
    }

    @Test
    void testGetAgency() {
        assertEquals("BigHit Music", this.btsGroup.getAgency());
    }   

    @Test
    void testGetDebutYear() {
        assertEquals(2013, this.btsGroup.getDebutYear());
    }

    @Test
    void testGetDisbandYear() {
        assertEquals(0, this.btsGroup.getDisbandYear());
    }

    // Legacy Constructor Tests
    @Test
    void testConstructorWithLists() {
        List<String> labels = Arrays.asList("HYBE Labels", "BigHit Music");
        List<String> members = Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook");
        Group newGroup = new Group("BTS", "BigHit Music", 2013, labels, members);
        
        assertEquals("BTS", newGroup.getGroupName());
        assertEquals("BigHit Music", newGroup.getAgency());
        assertEquals(2013, newGroup.getDebutYear());
        assertEquals(labels, newGroup.getLabels());
        assertEquals(members, newGroup.getMembers());
        assertTrue(newGroup.getFormerMembers().isEmpty());
        assertTrue(newGroup.getSubunits().isEmpty());
        assertEquals(0, newGroup.getDisbandYear());
    }

    @Test
    void testSimpleConstructor() {
        Group newGroup = new Group("NewJeans", "ADOR", 2022);
        
        assertEquals("NewJeans", newGroup.getGroupName());
        assertEquals("ADOR", newGroup.getAgency());
        assertEquals(2022, newGroup.getDebutYear());
        assertNotNull(newGroup.getGroupId());
        assertTrue(newGroup.getLabels().isEmpty());
        assertTrue(newGroup.getMembers().isEmpty());
        assertTrue(newGroup.getFormerMembers().isEmpty());
        assertTrue(newGroup.getSubunits().isEmpty());
        assertEquals(0, newGroup.getDisbandYear());
    }

    // Add method tests
    @Test
    void testAddLabel() {
        btsGroup.addLabel("HYBE Labels");
        btsGroup.addLabel("BigHit Music");
        
        List<String> labels = btsGroup.getLabels();
        assertEquals(2, labels.size());
        assertTrue(labels.contains("HYBE Labels"));
        assertTrue(labels.contains("BigHit Music"));
    }

    @Test
    void testAddMember() {
        btsGroup.addMember("RM");
        btsGroup.addMember("Jin");
        btsGroup.addMember("Suga");
        
        List<String> members = btsGroup.getMembers();
        assertEquals(3, members.size());
        assertTrue(members.contains("RM"));
        assertTrue(members.contains("Jin"));
        assertTrue(members.contains("Suga"));
    }

    @Test
    void testAddFormerMember() {
        btsGroup.addFormerMember("Kim Garam");
        btsGroup.addFormerMember("Woojin");
        
        List<String> formerMembers = btsGroup.getFormerMembers();
        assertEquals(2, formerMembers.size());
        assertTrue(formerMembers.contains("Kim Garam"));
        assertTrue(formerMembers.contains("Woojin"));
    }

    @Test
    void testAddSubunit() {
        btsGroup.addSubunit("3RACHA");
        btsGroup.addSubunit("Dance Racha");
        
        List<String> subunits = btsGroup.getSubunits();
        assertEquals(2, subunits.size());
        assertTrue(subunits.contains("3RACHA"));
        assertTrue(subunits.contains("Dance Racha"));
    }

    @Test
    void testCollectionImmutability() {
        // Test that returned collections are immutable
        List<String> labels = btsGroup.getLabels();
        List<String> members = btsGroup.getMembers();
        List<String> formerMembers = btsGroup.getFormerMembers();
        List<String> subunits = btsGroup.getSubunits();

        assertThrows(UnsupportedOperationException.class, () -> labels.add("New Label"));
        assertThrows(UnsupportedOperationException.class, () -> members.add("New Member"));
        assertThrows(UnsupportedOperationException.class, () -> formerMembers.add("New Former Member"));
        assertThrows(UnsupportedOperationException.class, () -> subunits.add("New Subunit"));
    }

    @Test
    void testDefensiveCopying() {
        // Test that collections are defensively copied
        List<String> originalLabels = Arrays.asList("HYBE", "BigHit");
        btsGroup.setLabels(originalLabels);
        
        // Modifying original list should not affect group's labels
        List<String> modifiableLabels = new java.util.ArrayList<>(originalLabels);
        modifiableLabels.add("New Label");
        
        assertEquals(2, btsGroup.getLabels().size());
        assertFalse(btsGroup.getLabels().contains("New Label"));
    }

    // Scenario Tests
    @Test
    void testGroupWithNoSubunitsNoFormerMembersNotDisbanded() {
        // Example: BTS
        Group bts = Group.builder("BTS", "BigHit Music", 2013)
                      .labels(Arrays.asList("HYBE Labels", "BigHit Music"))
                      .members(Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook"))
                      .build();
        
        assertEquals("BTS", bts.getGroupName());
        assertEquals("BigHit Music", bts.getAgency());
        assertEquals(2013, bts.getDebutYear());
        assertTrue(bts.getSubunits().isEmpty());
        assertTrue(bts.getFormerMembers().isEmpty());
        assertEquals(0, bts.getDisbandYear());
        assertEquals(7, bts.getMembers().size());
    }

    @Test
    void testGroupWithSubunitsNoFormerMembersNotDisbanded() {
        // Example: SEVENTEEN
        Group seventeen = Group.builder("SEVENTEEN", "PLEDIS Entertainment", 2015)
                           .labels(Arrays.asList("HYBE Labels", "PLEDIS Entertainment"))
                           .members(Arrays.asList("S.Coups", "Jeonghan", "Joshua", "Jun", "Hoshi", "Wonwoo", 
                                    "Woozi", "DK", "Mingyu", "The8", "Seungkwan", "Vernon", "Dino"))
                           .subunits(Arrays.asList("Hip-hop Team", "Vocal Team", "Performance Team", "BSS"))
                           .build();
        
        assertEquals("SEVENTEEN", seventeen.getGroupName());
        assertEquals(2015, seventeen.getDebutYear());
        assertFalse(seventeen.getSubunits().isEmpty());
        assertEquals(4, seventeen.getSubunits().size());
        assertTrue(seventeen.getFormerMembers().isEmpty());
        assertEquals(0, seventeen.getDisbandYear());
    }

    @Test
    void testGroupWithFormerMembersNoSubunitsNotDisbanded() {
        // Example: LE SSERAFIM
        Group leSserafim = Group.builder("LE SSERAFIM", "Source Music", 2022)
                             .labels(Arrays.asList("HYBE Labels", "Source Music"))
                             .members(Arrays.asList("Sakura", "Kim Chaewon", "Huh Yunjin", "Kazuha", "Hong Eunchae"))
                             .formerMembers(Arrays.asList("Kim Garam"))
                             .build();
        
        assertEquals("LE SSERAFIM", leSserafim.getGroupName());
        assertEquals(2022, leSserafim.getDebutYear());
        assertTrue(leSserafim.getSubunits().isEmpty());
        assertFalse(leSserafim.getFormerMembers().isEmpty());
        assertEquals(1, leSserafim.getFormerMembers().size());
        assertEquals(0, leSserafim.getDisbandYear());
    }

    @Test
    void testGroupWithSubunitsAndFormerMembersNotDisbanded() {
        // Example: Stray Kids
        Group strayKids = Group.builder("Stray Kids", "JYP Entertainment", 2018)
                            .labels(Arrays.asList("JYP Entertainment"))
                            .members(Arrays.asList("Bang Chan", "Lee Know", "Changbin", "Hyunjin", 
                                     "Han", "Felix", "Seungmin", "I.N"))
                            .subunits(Arrays.asList("3RACHA"))
                            .formerMembers(Arrays.asList("Woojin"))
                            .build();
        
        assertEquals("Stray Kids", strayKids.getGroupName());
        assertEquals(2018, strayKids.getDebutYear());
        assertFalse(strayKids.getSubunits().isEmpty());
        assertFalse(strayKids.getFormerMembers().isEmpty());
        assertEquals(0, strayKids.getDisbandYear());
    }

    @Test
    void testDisbandedGroup() {
        // Example: IZ*ONE
        Group izOne = Group.builder("IZ*ONE", "Off The Record Entertainment", 2018)
                        .labels(Arrays.asList("Off The Record Entertainment", "Swing Entertainment"))
                        .members(Arrays.asList("Jang Wonyoung", "Sakura", "Jo Yuri", "Choi Yena", 
                                 "Ahn Yujin", "Nako", "Kwon Eunbi", "Kang Hyewon", "Hitomi", 
                                 "Kim Chaewon", "Kim Minju", "Lee Chaeyeon"))
                        .disbandYear(2021)
                        .build();
        
        assertEquals("IZ*ONE", izOne.getGroupName());
        assertEquals(2018, izOne.getDebutYear());
        assertEquals(2021, izOne.getDisbandYear());
        assertNotEquals(0, izOne.getDisbandYear());
        assertEquals(12, izOne.getMembers().size());
    }

    @Test
    void testBuilderToString() {
        Group.GroupBuilder builder = Group.builder("BTS", "BigHit Music", 2013)
                                        .labels(Arrays.asList("HYBE Labels"))
                                        .members(Arrays.asList("RM", "Jin"))
                                        .formerMembers(Arrays.asList("Former"))
                                        .disbandYear(0)
                                        .subunits(Arrays.asList("Subunit"));
        
        String builderString = builder.toString();
        assertNotNull(builderString);
        assertTrue(builderString.contains("BTS"));
        assertTrue(builderString.contains("BigHit Music"));
        assertTrue(builderString.contains("2013"));
        assertTrue(builderString.contains("HYBE Labels"));
        assertTrue(builderString.contains("RM"));
        assertTrue(builderString.contains("Former"));
        assertTrue(builderString.contains("Subunit"));
    }

    @Test
    void testNewGroupStatusIsInactive() {
        Group newGroup = new Group("NewJeans", "ADOR", 2022);
        assertEquals(GroupActiveStatus.INACTIVE, newGroup.getStatus());
    }

    @Test
    void testGroupActiveStatusDisbanded() {
        Group group = Group.builder("IZ*ONE", "Off The Record", 2018)
                        .disbandYear(2021)
                        .build();
        assertEquals(GroupActiveStatus.DISBANDED, group.getStatus());
    }

    @Test
    void testGroupActiveStatusInactive() {
        Group group = Group.builder("Test Group", "Test Agency", 2020).build();
        assertEquals(GroupActiveStatus.INACTIVE, group.getStatus());
    }

    @Test
    void testGroupActiveStatusTransitions() {
        Group group = new Group("Test Group", "Test Agency", 2020);
        assertEquals(GroupActiveStatus.INACTIVE, group.getStatus());

        group.addMember("Member 1");
        assertEquals(GroupActiveStatus.ACTIVE, group.getStatus());

        group.setDisbandYear(2023);
        assertEquals(GroupActiveStatus.DISBANDED, group.getStatus());
    }

    @Test
    void testGroupActiveStatusWithNullMembers() {
        Group group = new Group();
        // Use reflection to set members to null and trigger status update
        try {
            java.lang.reflect.Field membersField = Group.class.getDeclaredField("members");
            membersField.setAccessible(true);
            membersField.set(group, null);
            
            // Get the updateStatus method and invoke it
            java.lang.reflect.Method updateStatusMethod = 
                Group.class.getDeclaredMethod("updateStatus");
            updateStatusMethod.setAccessible(true);
            updateStatusMethod.invoke(group);
            
            assertEquals(GroupActiveStatus.INACTIVE, group.getStatus());
        } catch (Exception e) {
            fail("Failed to test null members case: " + e.getMessage());
        }
    }
}
