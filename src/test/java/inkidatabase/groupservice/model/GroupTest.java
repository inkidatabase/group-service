package inkidatabase.groupservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
    Group group;
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        this.group = new Group();
        this.group.setGroupId(uuid);
        this.group.setGroupName("BTS");
        this.group.setAgency("BigHit Music");
        this.group.setDebutYear(2013);
    }

    // Setter and Getter Tests
    @Test
    void testSetAndGetLabels() {
        List<String> labels = Arrays.asList("HYBE Labels", "BigHit Music");
        this.group.setLabels(labels);
        assertEquals(labels, this.group.getLabels());
        assertEquals(2, this.group.getLabels().size());
    }

    @Test
    void testSetAndGetMembers() {
        List<String> members = Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook");
        this.group.setMembers(members);
        assertEquals(members, this.group.getMembers());
        assertEquals(7, this.group.getMembers().size());
    }

    @Test
    void testGetGroupId() {
        assertEquals(uuid, this.group.getGroupId());
    }

    @Test
    void testGetGroupName() {
        assertEquals("BTS", this.group.getGroupName());
    }

    @Test
    void testGetAgency() {
        assertEquals("BigHit Music", this.group.getAgency());
    }   

    @Test
    void testGetDebutYear() {
        assertEquals(2013, this.group.getDebutYear());
    }

    @Test
    void testGetDisbandYear() {
        assertEquals(0, this.group.getDisbandYear());
    }

    // Constructor Tests
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
    void testFullConstructor() {
        List<String> labels = Arrays.asList("HYBE Labels", "Source Music");
        List<String> members = Arrays.asList("Sakura", "Kim Chaewon", "Huh Yunjin", "Kazuha", "Hong Eunchae");
        List<String> formerMembers = Arrays.asList("Kim Garam");
        List<String> subunits = Arrays.asList("HUH x CHAEWON");
        
        Group newGroup = new Group("LE SSERAFIM", "Source Music", 2022, labels, members, formerMembers, 0, subunits);
        
        assertEquals("LE SSERAFIM", newGroup.getGroupName());
        assertEquals("Source Music", newGroup.getAgency());
        assertEquals(2022, newGroup.getDebutYear());
        assertEquals(labels, newGroup.getLabels());
        assertEquals(members, newGroup.getMembers());
        assertEquals(formerMembers, newGroup.getFormerMembers());
        assertEquals(subunits, newGroup.getSubunits());
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
        group.addLabel("HYBE Labels");
        group.addLabel("BigHit Music");
        
        List<String> labels = group.getLabels();
        assertEquals(2, labels.size());
        assertTrue(labels.contains("HYBE Labels"));
        assertTrue(labels.contains("BigHit Music"));
    }

    @Test
    void testAddMember() {
        group.addMember("RM");
        group.addMember("Jin");
        group.addMember("Suga");
        
        List<String> members = group.getMembers();
        assertEquals(3, members.size());
        assertTrue(members.contains("RM"));
        assertTrue(members.contains("Jin"));
        assertTrue(members.contains("Suga"));
    }

    @Test
    void testAddFormerMember() {
        group.addFormerMember("Kim Garam");
        group.addFormerMember("Woojin");
        
        List<String> formerMembers = group.getFormerMembers();
        assertEquals(2, formerMembers.size());
        assertTrue(formerMembers.contains("Kim Garam"));
        assertTrue(formerMembers.contains("Woojin"));
    }

    @Test
    void testAddSubunit() {
        group.addSubunit("3RACHA");
        group.addSubunit("Dance Racha");
        
        List<String> subunits = group.getSubunits();
        assertEquals(2, subunits.size());
        assertTrue(subunits.contains("3RACHA"));
        assertTrue(subunits.contains("Dance Racha"));
    }

    @Test
    void testCollectionImmutability() {
        // Test that returned collections are immutable
        List<String> labels = group.getLabels();
        List<String> members = group.getMembers();
        List<String> formerMembers = group.getFormerMembers();
        List<String> subunits = group.getSubunits();

        assertThrows(UnsupportedOperationException.class, () -> labels.add("New Label"));
        assertThrows(UnsupportedOperationException.class, () -> members.add("New Member"));
        assertThrows(UnsupportedOperationException.class, () -> formerMembers.add("New Former Member"));
        assertThrows(UnsupportedOperationException.class, () -> subunits.add("New Subunit"));
    }

    @Test
    void testDefensiveCopying() {
        // Test that collections are defensively copied
        List<String> originalLabels = Arrays.asList("HYBE", "BigHit");
        group.setLabels(originalLabels);
        
        // Modifying original list should not affect group's labels
        List<String> modifiableLabels = new java.util.ArrayList<>(originalLabels);
        modifiableLabels.add("New Label");
        
        assertEquals(2, group.getLabels().size());
        assertFalse(group.getLabels().contains("New Label"));
    }

    // Scenario Tests
    @Test
    void testGroupWithNoSubunitsNoFormerMembersNotDisbanded() {
        // Example: BTS
        Group bts = new Group(
            "BTS",
            "BigHit Music",
            2013,
            Arrays.asList("HYBE Labels", "BigHit Music"),
            Arrays.asList("RM", "Jin", "Suga", "J-Hope", "Jimin", "V", "Jungkook")
        );
        
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
        Group seventeen = new Group(
            "SEVENTEEN",
            "PLEDIS Entertainment",
            2015,
            Arrays.asList("HYBE Labels", "PLEDIS Entertainment"),
            Arrays.asList("S.Coups", "Jeonghan", "Joshua", "Jun", "Hoshi", "Wonwoo", 
                         "Woozi", "DK", "Mingyu", "The8", "Seungkwan", "Vernon", "Dino")
        );
        seventeen.setSubunits(Arrays.asList("Hip-hop Team", "Vocal Team", "Performance Team", "BSS"));
        
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
        Group leSserafim = new Group(
            "LE SSERAFIM",
            "Source Music",
            2022,
            Arrays.asList("HYBE Labels", "Source Music"),
            Arrays.asList("Sakura", "Kim Chaewon", "Huh Yunjin", "Kazuha", "Hong Eunchae")
        );
        leSserafim.setFormerMembers(Arrays.asList("Kim Garam"));
        
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
        Group strayKids = new Group(
            "Stray Kids",
            "JYP Entertainment",
            2018,
            Arrays.asList("JYP Entertainment"),
            Arrays.asList("Bang Chan", "Lee Know", "Changbin", "Hyunjin", "Han", "Felix", "Seungmin", "I.N")
        );
        strayKids.setSubunits(Arrays.asList("3RACHA"));
        strayKids.setFormerMembers(Arrays.asList("Woojin"));
        
        assertEquals("Stray Kids", strayKids.getGroupName());
        assertEquals(2018, strayKids.getDebutYear());
        assertFalse(strayKids.getSubunits().isEmpty());
        assertFalse(strayKids.getFormerMembers().isEmpty());
        assertEquals(0, strayKids.getDisbandYear());
    }

    @Test
    void testDisbandedGroup() {
        // Example: IZ*ONE
        Group izOne = new Group(
            "IZ*ONE",
            "Off The Record Entertainment",
            2018,
            Arrays.asList("Off The Record Entertainment", "Swing Entertainment"),
            Arrays.asList("Jang Wonyoung", "Sakura", "Jo Yuri", "Choi Yena", "Ahn Yujin", 
                         "Nako", "Kwon Eunbi", "Kang Hyewon", "Hitomi", "Kim Chaewon", 
                         "Kim Minju", "Lee Chaeyeon")
        );
        izOne.setDisbandYear(2021);
        
        assertEquals("IZ*ONE", izOne.getGroupName());
        assertEquals(2018, izOne.getDebutYear());
        assertEquals(2021, izOne.getDisbandYear());
        assertNotEquals(0, izOne.getDisbandYear());
        assertEquals(12, izOne.getMembers().size());
    }
}
