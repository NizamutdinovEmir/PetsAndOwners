package nizamutdinov.;

import lombok.*;


import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private ColorPets color;
    private Long ownerId;
    private List<Long> friendIds;
}
