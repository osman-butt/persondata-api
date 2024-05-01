package dk.persondata.AgifyService;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgifyDTO {
    private String name;
    private int age;
    private int count;
}
