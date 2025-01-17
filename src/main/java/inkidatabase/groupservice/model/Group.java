package inkidatabase.groupservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter 
@NoArgsConstructor
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
        this(groupName, agency, debutYear, Collections.emptyList(), Collections.emptyList());
    }

    public Group(String groupName, String agency, int debutYear, List<String> labels, List<String> members) {
        this(groupName, agency, debutYear, labels, members, Collections.emptyList(), 0, Collections.emptyList());
    }

    public Group(String groupName, String agency, int debutYear, List<String> labels, List<String> members, 
                List<String> formerMembers, int disbandYear, List<String> subunits) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.agency = agency;
        this.debutYear = debutYear;
        this.labels = new ArrayList<>(labels);
        this.members = new ArrayList<>(members);
        this.formerMembers = new ArrayList<>(formerMembers);
        this.disbandYear = disbandYear;
        this.subunits = new ArrayList<>(subunits);
    }

    // Provide immutable views of the collections
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
    }

    // Setters for collections with defensive copying
    public void setLabels(List<String> labels) {
        this.labels = new ArrayList<>(labels);
    }

    public void setMembers(List<String> members) {
        this.members = new ArrayList<>(members);
    }

    public void setFormerMembers(List<String> formerMembers) {
        this.formerMembers = new ArrayList<>(formerMembers);
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
    }

    public void addFormerMember(String member) {
        this.formerMembers.add(member);
    }

    public void addSubunit(String subunit) {
        this.subunits.add(subunit);
    }
}
