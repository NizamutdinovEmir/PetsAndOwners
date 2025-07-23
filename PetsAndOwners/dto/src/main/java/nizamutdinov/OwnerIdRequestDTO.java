package nizamutdinov.;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OwnerIdRequestDTO {
    @NotNull
    private Long id;
}
