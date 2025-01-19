package inkidatabase.groupservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CreateGroupRequest {
    @NotBlank(message = "Group name is required")
    private String groupName;

    @NotBlank(message = "Agency is required")
    private String agency;

    private List<String> labels;

    @NotNull(message = "Members list cannot be null")
    private List<String> members;

    private List<String> formerMembers;

    @NotNull(message = "Debut year is required")
    @Min(value = 1900, message = "Debut year must be after 1900")
    private Integer debutYear;

    private Integer disbandYear;

    private List<String> subunits;
    
    private List<String> socialLinks;
}
