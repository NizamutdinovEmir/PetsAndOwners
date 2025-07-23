package nizamutdinov.;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateRequest {
    private Long    id;
    private String  name;
    private String  type;
    private LocalDate birthDate;
}