package dk.persondata.person;

import lombok.*;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public Person(String firstName, String middleName, String lastName) {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName = capitalize(firstName != null ? firstName.trim() : null);
    }

    public void setMiddleName(String middleName) {
        this.middleName = capitalize(middleName != null ? middleName.trim() : null);
    }

    public void setLastName(String lastName) {
        this.lastName = capitalize(lastName != null ? lastName.trim() : null);
    }

    public String getFullName() {
        return getFirstName()  + (getMiddleName()!=null ? " " + getMiddleName() : "") + (getLastName()!=null ? " " + getLastName() : "");
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return;
        }

        fullName = fullName.replaceAll("\\s+", " ").trim();

        int firstSpace = fullName.indexOf(" ");
        int lastSpace = fullName.lastIndexOf(" ");

        if(firstSpace == -1){
            setFirstName(fullName);
            setMiddleName(null);
            setLastName(null);
        } else if (lastSpace == firstSpace){
            setFirstName(fullName.substring(0, firstSpace));
            setMiddleName(null);
            setLastName(fullName.substring(firstSpace + 1));
        } else {
            setFirstName(fullName.substring(0, firstSpace));
            setMiddleName(fullName.substring(firstSpace + 1, lastSpace));
            setLastName(fullName.substring(lastSpace + 1));
        }
    }

    public String capitalize(String name) {
        if (name == null) {
            return null;
        }
        if (name.contains(" ")){
            int space = name.indexOf(" ");
            return capitalize(name.substring(0, space)) + " " + capitalize(name.substring(space + 1));
        }

        if(name.startsWith("Mc")) return "Mc" + capitalize(name.substring(2));

        if(name.contains("-")) return Arrays.stream(name.split("-")).map(this::capitalize).collect(Collectors.joining("-"));

        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
