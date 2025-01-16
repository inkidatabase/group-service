package inkidatabase.groupservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor
public class Group {
    private UUID groupId;
    private String groupName;
    private String agency;
    private List<String> labels = new ArrayList<>(); // company labels
    private List<String> members = new ArrayList<>(); // current members
    private List<String> formerMembers = new ArrayList<>(); // former members (optional)
    private int debutYear;
    private int disbandYear; // disband year (optional)
    private List<String> subunits = new ArrayList<>(); // subunits (optional)

    // Simple constructor for testing
    public Group(String groupName, String agency, int debutYear) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.agency = agency;
        this.debutYear = debutYear;
        this.labels = new ArrayList<>();
        this.members = new ArrayList<>();
        this.formerMembers = new ArrayList<>();
        this.subunits = new ArrayList<>();
        this.disbandYear = 0;
    }

    public Group(String groupName, String agency, int debutYear, List<String> labels, List<String> members) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.agency = agency;
        this.debutYear = debutYear;
        this.labels = new ArrayList<>(labels);
        this.members = new ArrayList<>(members);
        this.formerMembers = new ArrayList<>();
        this.subunits = new ArrayList<>();
        this.disbandYear = 0; // 0 means not disbanded
    }

    public Group(String groupName, String agency, int debutYear, List<String> labels, List<String> members, 
                List<String> formerMembers, int disbandYear, List<String> subunits) {
        this(groupName, agency, debutYear, labels, members);
        this.formerMembers = new ArrayList<>(formerMembers);
        this.disbandYear = disbandYear;
        this.subunits = new ArrayList<>(subunits);
    }
}
