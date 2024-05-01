package dk.persondata.NationalizeService;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDTO {
    private String country_id;
    private Double probability;
}
