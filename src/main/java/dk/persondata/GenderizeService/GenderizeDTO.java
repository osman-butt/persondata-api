package dk.persondata.GenderizeService;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenderizeDTO {
    private int count;
    private String name;
    private String gender;
    private Double probability;
}
