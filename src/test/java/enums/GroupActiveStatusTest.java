package enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GroupActiveStatusTest {
    
    @Test
    void testEnumValues() {
        // Test that all expected enum values exist
        assertEquals(3, GroupActiveStatus.values().length);
        assertNotNull(GroupActiveStatus.ACTIVE);
        assertNotNull(GroupActiveStatus.INACTIVE);
        assertNotNull(GroupActiveStatus.DISBANDED);
    }

    @Test
    void testEnumStringValues() {
        // Test that string values are correctly set
        assertEquals("ACTIVE", GroupActiveStatus.ACTIVE.getValue());
        assertEquals("INACTIVE", GroupActiveStatus.INACTIVE.getValue());
        assertEquals("DISBANDED", GroupActiveStatus.DISBANDED.getValue());
    }

    @Test
    void testContainsMethod() {
        // Test valid status values
        assertTrue(GroupActiveStatus.contains("ACTIVE"));
        assertTrue(GroupActiveStatus.contains("INACTIVE"));
        assertTrue(GroupActiveStatus.contains("DISBANDED"));

        // Test invalid status values
        assertFalse(GroupActiveStatus.contains("UNKNOWN"));
        assertFalse(GroupActiveStatus.contains(""));
        assertFalse(GroupActiveStatus.contains(null));
    }

    @Test
    void testCaseSensitivity() {
        // Test that the contains method is case-sensitive
        assertFalse(GroupActiveStatus.contains("active"));
        assertFalse(GroupActiveStatus.contains("Active"));
        assertFalse(GroupActiveStatus.contains("ACTIVE "));
        assertFalse(GroupActiveStatus.contains(" ACTIVE"));
    }

    @Test
    void testValueUniqueness() {
        // Test that all enum values have unique string representations
        String[] values = new String[GroupActiveStatus.values().length];
        int index = 0;
        for (GroupActiveStatus status : GroupActiveStatus.values()) {
            for (int i = 0; i < index; i++) {
                assertNotEquals(values[i], status.getValue(), 
                    "Enum values must be unique");
            }
            values[index++] = status.getValue();
        }
    }
}
