package nizamutdinov.;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerUpdateRequest {
    private Long    id;
    private String  name;
    private LocalDate birthDate;
    private String  username;
    private String  password;
}