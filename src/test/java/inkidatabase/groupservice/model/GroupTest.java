package inkidatabase.groupservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
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
    void testAddFormerMemberComprehensive() {
        // Test valid former member addition
        String validFormerMember = "Kim Garam";
        btsGroup.addFormerMember(validFormerMember);
        assertTrue(btsGroup.getFormerMembers().contains(validFormerMember));
        
        // Test null former member
        int sizeBeforeNull = btsGroup.getFormerMembers().size();
        btsGroup.addFormerMember(null);
        assertEquals(sizeBeforeNull, btsGroup.getFormerMembers().size());
        
        // Test empty string former member
        int sizeBeforeEmpty = btsGroup.getFormerMembers().size();
        btsGroup.addFormerMember("");
        assertEquals(sizeBeforeEmpty, btsGroup.getFormerMembers().size());
        
        // Test adding multiple valid former members
        btsGroup.addFormerMember("Woojin");
        assertEquals(2, btsGroup.getFormerMembers().size());
        assertTrue(btsGroup.getFormerMembers().contains("Woojin"));
    }

    @Test
    void testFormerMemberValidation() {
        // Test valid former member
        assertTrue(btsGroup.isMemberValid("Kim Garam"));
        
        // Test invalid former members
        assertFalse(btsGroup.isMemberValid(""));
        assertFalse(btsGroup.isMemberValid(null));
        
        // Test former member validation affects addition
        int initialSize = btsGroup.getFormerMembers().size();
        btsGroup.addFormerMember("");
        assertEquals(initialSize, btsGroup.getFormerMembers().size());
        btsGroup.addFormerMember(null);
        assertEquals(initialSize, btsGroup.getFormerMembers().size());
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

    // Happy Path Validation Tests
    @Test
    void testValidGroup() {
        Group validGroup = Group.builder("BTS", "BigHit Music", 2013)
                               .labels(Arrays.asList("HYBE Labels"))
                               .members(Arrays.asList("RM", "Jin"))
                               .build();
        
        assertTrue(validGroup.isValid());
        assertTrue(validGroup.isGroupNameValid());
        assertTrue(validGroup.isAgencyValid());
        assertTrue(validGroup.isDebutYearValid());
        assertTrue(validGroup.isDisbandYearValid());
    }

    @Test
    void testValidDisbandedGroup() {
        Group disbandedGroup = Group.builder("2NE1", "YG Entertainment", 2009)
                                  .disbandYear(2016)
                                  .build();
        
        assertTrue(disbandedGroup.isValid());
        assertTrue(disbandedGroup.isDisbandYearValid());
    }

    @Test
    void testValidMemberOperations() {
        String validMember = "Jungkook";
        assertTrue(btsGroup.isMemberValid(validMember));
        btsGroup.addMember(validMember);
        assertTrue(btsGroup.getMembers().contains(validMember));
    }

    @Test
    void testValidLabelOperations() {
        String validLabel = "HYBE Labels";
        assertTrue(btsGroup.isLabelValid(validLabel));
        btsGroup.addLabel(validLabel);
        assertTrue(btsGroup.getLabels().contains(validLabel));
    }

    // Unhappy Path Validation Tests
    @Test
    void testInvalidGroupName() {
        Group invalidGroup = Group.builder("", "BigHit Music", 2013).build();
        assertFalse(invalidGroup.isGroupNameValid());
        assertFalse(invalidGroup.isValid());
        
        invalidGroup = Group.builder(null, "BigHit Music", 2013).build();
        assertFalse(invalidGroup.isGroupNameValid());
        assertFalse(invalidGroup.isValid());
    }

    @Test
    void testInvalidAgency() {
        Group invalidGroup = Group.builder("BTS", "", 2013).build();
        assertFalse(invalidGroup.isAgencyValid());
        assertFalse(invalidGroup.isValid());
        
        invalidGroup = Group.builder("BTS", null, 2013).build();
        assertFalse(invalidGroup.isAgencyValid());
        assertFalse(invalidGroup.isValid());
    }

    @Test
    void testInvalidDebutYear() {
        Group invalidGroup = Group.builder("BTS", "BigHit Music", 1800).build();
        assertFalse(invalidGroup.isDebutYearValid());
        assertFalse(invalidGroup.isValid());
        
        invalidGroup = Group.builder("BTS", "BigHit Music", 2030).build();
        assertFalse(invalidGroup.isDebutYearValid());
        assertFalse(invalidGroup.isValid());
    }

    @Test
    void testInvalidDisbandYear() {
        // Disband year before debut year
        Group invalidGroup = Group.builder("2NE1", "YG Entertainment", 2009)
                                .disbandYear(2008)
                                .build();
        assertFalse(invalidGroup.isDisbandYearValid());
        assertFalse(invalidGroup.isValid());
        
        // Future disband year
        invalidGroup = Group.builder("2NE1", "YG Entertainment", 2009)
                          .disbandYear(2030)
                          .build();
        assertFalse(invalidGroup.isDisbandYearValid());
        assertFalse(invalidGroup.isValid());
    }

    @Test
    void testInvalidMember() {
        assertFalse(btsGroup.isMemberValid(""));
        assertFalse(btsGroup.isMemberValid(null));
        
        // Add invalid member and verify it's not added
        int initialSize = btsGroup.getMembers().size();
        btsGroup.addMember("");
        assertEquals(initialSize, btsGroup.getMembers().size()); // Should not add invalid member
        btsGroup.addMember(null);
        assertEquals(initialSize, btsGroup.getMembers().size()); // Should not add null member
    }

    @Test
    void testInvalidLabel() {
        assertFalse(btsGroup.isLabelValid(""));
        assertFalse(btsGroup.isLabelValid(null));
        
        // Add invalid label and verify it's not added
        int initialSize = btsGroup.getLabels().size();
        btsGroup.addLabel("");
        assertEquals(initialSize, btsGroup.getLabels().size()); // Should not add invalid label
        btsGroup.addLabel(null);
        assertEquals(initialSize, btsGroup.getLabels().size()); // Should not add null label
    }

    @Test
    void testInvalidSubunit() {
        assertFalse(btsGroup.isSubunitValid(""));
        assertFalse(btsGroup.isSubunitValid(null));
        
        // Add invalid subunit and verify it's not added
        int initialSize = btsGroup.getSubunits().size();
        btsGroup.addSubunit("");
        assertEquals(initialSize, btsGroup.getSubunits().size()); // Should not add invalid subunit
        btsGroup.addSubunit(null);
        assertEquals(initialSize, btsGroup.getSubunits().size()); // Should not add null subunit
    }
}
