package inkidatabase.groupservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

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
}
