package enums;

import lombok.Getter;

@Getter
public enum GroupActiveStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DISBANDED("DISBANDED");

    private final String value;

    GroupActiveStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (GroupActiveStatus status : GroupActiveStatus.values()) {
            if (status.getValue().equals(param)) {
                return true;
            }
        }
        return false;
    }
}
