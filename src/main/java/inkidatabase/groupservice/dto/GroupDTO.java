package inkidatabase.groupservice.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GroupDTO {
    private UUID groupId;
    private String groupName;
    private String agency;
    private List<String> labels;
    private List<String> members;
    private List<String> formerMembers;
    private int debutYear;
    private Integer disbandYear;  // Using Integer to allow null
    private List<String> subunits;
    private List<String> socialLinks;
}
