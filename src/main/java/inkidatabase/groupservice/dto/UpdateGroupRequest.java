package inkidatabase.groupservice.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UpdateGroupRequest {
    private String groupName;
    private String agency;
    private List<String> labels;
    private List<String> members;
    private List<String> formerMembers;
    
    @Min(value = 1900, message = "Debut year must be after 1900")
    private Integer debutYear;
    private Integer disbandYear;
    private List<String> subunits;
    private List<String> socialLinks;
}
