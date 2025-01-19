package inkidatabase.groupservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import enums.GroupActiveStatus;

@Getter 
@NoArgsConstructor
public class Group {
    private UUID groupId = UUID.randomUUID();  // Initialize with a default value
    private String groupName;
    private String agency;
    private List<String> labels = new ArrayList<>();
    private List<String> members = new ArrayList<>();
    private List<String> formerMembers = new ArrayList<>();
    private int debutYear;
    private int disbandYear;
    private List<String> subunits = new ArrayList<>();
    private GroupActiveStatus status = GroupActiveStatus.ACTIVE;

    // Simple constructor for testing
    public Group(String groupName, String agency, int debutYear) {
        this();
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.agency = agency;
        this.debutYear = debutYear;
        updateStatus();
    }

    public Group(String groupName, String agency, int debutYear, List<String> labels, List<String> members) {
        this(groupName, agency, debutYear);
        setLabels(labels);
        setMembers(members);
    }

    @Builder(builderMethodName = "internalBuilder")
    private Group(String groupName, String agency, int debutYear, List<String> labels, 
                List<String> members, List<String> formerMembers, int disbandYear, List<String> subunits) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.agency = agency;
        this.debutYear = debutYear;
        this.labels = labels != null ? new ArrayList<>(labels) : new ArrayList<>();
        this.members = members != null ? new ArrayList<>(members) : new ArrayList<>();
        this.formerMembers = formerMembers != null ? new ArrayList<>(formerMembers) : new ArrayList<>();
        this.disbandYear = disbandYear;
        this.subunits = subunits != null ? new ArrayList<>(subunits) : new ArrayList<>();
        updateStatus();
    }

    // Static builder method with required parameters
    public static GroupBuilder builder(String groupName, String agency, int debutYear) {
        return internalBuilder()
                .groupName(groupName)
                .agency(agency)
                .debutYear(debutYear);
    }

    // Collection getters that provide immutable views
    public List<String> getLabels() {
        return Collections.unmodifiableList(labels);
    }

    public List<String> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<String> getFormerMembers() {
        return Collections.unmodifiableList(formerMembers);
    }

    public List<String> getSubunits() {
        return Collections.unmodifiableList(subunits);
    }

    // Setters for non-collection fields
    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setDebutYear(int debutYear) {
        this.debutYear = debutYear;
    }

    public void setDisbandYear(int disbandYear) {
        this.disbandYear = disbandYear;
        updateStatus();
    }

    // Setters for collections with defensive copying
    public void setLabels(List<String> labels) {
        this.labels = new ArrayList<>(labels);
    }

    public void setMembers(List<String> members) {
        this.members = new ArrayList<>(members);
        updateStatus();
    }

    public void setFormerMembers(List<String> formerMembers) {
        this.formerMembers = new ArrayList<>(formerMembers);
        updateStatus();
    }

    public void setSubunits(List<String> subunits) {
        this.subunits = new ArrayList<>(subunits);
    }

    // Methods to modify collections
    public void addLabel(String label) {
        this.labels.add(label);
    }

    public void addMember(String member) {
        this.members.add(member);
        updateStatus();
    }

    public void addFormerMember(String member) {
        this.formerMembers.add(member);
        updateStatus();
    }

    public void addSubunit(String subunit) {
        this.subunits.add(subunit);
    }

    private void updateStatus() {
        if (disbandYear > 0) {
            status = GroupActiveStatus.DISBANDED;
        } else if (members == null || members.isEmpty()) {
            status = GroupActiveStatus.INACTIVE;
        } else {
            status = GroupActiveStatus.ACTIVE;
        }
    }
}
