package nizamutdinov.;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthDate;

    private List<Long> petIds;

    private String username;

    private String password;
}