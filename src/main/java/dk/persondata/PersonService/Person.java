package dk.persondata.PersonService;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    private String fullName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private Double genderProbability;
    private int age;
    private Double ageProbability;
    private String country;
    private Double countryProbability;
}
