package inkidatabase.groupservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import enums.GroupActiveStatus;

@Entity
@Table(name = "groups")
@Getter 
@NoArgsConstructor
@ToString(exclude = "groupId")
public class Group {
    @Id
    @Column(name = "group_id")
    private UUID groupId = UUID.randomUUID();  // Initialize with a default value

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(nullable = false)
    private String agency;

    @ElementCollection
    @CollectionTable(name = "group_labels", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "label")
    private List<String> labels = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "member")
    private List<String> members = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "group_former_members", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "former_member")
    private List<String> formerMembers = new ArrayList<>();

    @Column(name = "debut_year", nullable = false)
    private int debutYear;

    @Column(name = "disband_year")
    private int disbandYear;

    @ElementCollection
    @CollectionTable(name = "group_subunits", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "subunit")
    private List<String> subunits = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "group_social_links", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "social_link")
    private List<String> socialLinks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
        if (labels != null) {
            setLabels(labels);
        }
        if (members != null) {
            setMembers(members);
        }
    }

    @Builder(builderMethodName = "internalBuilder")
    private Group(String groupName, String agency, int debutYear, List<String> labels, 
                List<String> members, List<String> formerMembers, int disbandYear, List<String> subunits, List<String> socialLinks) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.agency = agency;
        this.debutYear = debutYear;
        this.labels = labels != null ? new ArrayList<>(labels) : new ArrayList<>();
        this.members = members != null ? new ArrayList<>(members) : new ArrayList<>();
        this.formerMembers = formerMembers != null ? new ArrayList<>(formerMembers) : new ArrayList<>();
        this.disbandYear = disbandYear;
        this.subunits = subunits != null ? new ArrayList<>(subunits) : new ArrayList<>();
        this.socialLinks = socialLinks != null ? new ArrayList<>(socialLinks) : new ArrayList<>();
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

    public List<String> getSocialLinks() {
        return Collections.unmodifiableList(socialLinks);
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
        this.members = members != null ? new ArrayList<>(members) : new ArrayList<>();
        updateStatus();
    }

    public void setFormerMembers(List<String> formerMembers) {
        this.formerMembers = new ArrayList<>(formerMembers);
        updateStatus();
    }

    public void setSubunits(List<String> subunits) {
        this.subunits = new ArrayList<>(subunits);
    }

    public void setSocialLinks(List<String> socialLinks) {
        this.socialLinks = new ArrayList<>(socialLinks);
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

    public void addSocialLink(String socialLink) {
        this.socialLinks.add(socialLink);
    }

    void updateStatus() {
        if (disbandYear > 0) {
            status = GroupActiveStatus.DISBANDED;
        } else if (members == null || members.isEmpty()) {
            status = GroupActiveStatus.INACTIVE;
        } else {
            status = GroupActiveStatus.ACTIVE;
        }
    }
}
