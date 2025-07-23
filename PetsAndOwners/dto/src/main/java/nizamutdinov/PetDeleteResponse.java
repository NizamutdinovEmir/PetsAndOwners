package nizamutdinov.;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDeleteResponse {
    private Long id;
    private boolean deleted;
}