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
    private List<String> labels = new ArrayList<>();
    private List<String> members = new ArrayList<>();
    private List<String> formerMembers = new ArrayList<>();
    private int debutYear;
    private Integer disbandYear;
    private List<String> subunits = new ArrayList<>();

    public Group(String groupName, String agency, int debutYear) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.agency = agency;
        this.debutYear = debutYear;
    }
}
