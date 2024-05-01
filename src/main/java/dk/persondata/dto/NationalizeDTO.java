package dk.persondata.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NationalizeDTO {
    private int count;
    private String name;
    private List<CountryDTO> country;
}
