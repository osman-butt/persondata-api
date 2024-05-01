package dk.persondata.person;

import dk.persondata.dto.AgifyDTO;
import dk.persondata.apiService.ApiService;
import dk.persondata.dto.GenderizeDTO;
import dk.persondata.dto.NationalizeDTO;
import dk.persondata.exception.UnprocessableContent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {
    private final ApiService<AgifyDTO> agifyService;
    private final ApiService<GenderizeDTO> genderizeService;
    private final ApiService<NationalizeDTO> nationalizeService;
    private final Map<String, Person> cache = new HashMap<>();

    public PersonServiceImpl (ApiService<AgifyDTO> agifyService, ApiService<GenderizeDTO> genderizeService, ApiService<NationalizeDTO> nationalizeService) {
        this.agifyService = agifyService;
        this.genderizeService = genderizeService;
        this.nationalizeService = nationalizeService;
    }

    public Mono<Person> getPerson(String firstName, String middleName, String lastName) {
        Person person = new Person(firstName, middleName, lastName);

        if (firstName == null && lastName == null) {
            throw new UnprocessableContent("Invalid or missing firstName or lastName parameter");
        }

        Person cachedPerson = cachedPerson(person);

        if(cachedPerson != null) {
            return Mono.just(cachedPerson);
        }

        String searchFirstNameElseLastName = firstName != null ? firstName : lastName;
        String searchLastNameElseFirstName = lastName != null ? lastName : firstName;

        return Mono.zip(agifyService.fetch(searchFirstNameElseLastName), genderizeService.fetch(searchFirstNameElseLastName), nationalizeService.fetch(searchLastNameElseFirstName))
                .flatMap(tuple -> {
                    Person personResponse = toEntity(person, tuple.getT1(), tuple.getT2(), tuple.getT3());
                    cacheResult(personResponse);
                    return Mono.just(personResponse);
                });
    }

    // From dtos to Person object
    private Person toEntity(Person person, AgifyDTO agify, GenderizeDTO genderize, NationalizeDTO nationalize) {
        return Person.builder()
                .fullName(person.getFullName())
                .firstName(person.getFirstName())
                .middleName(person.getMiddleName())
                .lastName(person.getLastName())
                .gender(genderize.getGender())
                .genderProbability(genderize.getProbability() != null ? genderize.getProbability() : null)
                .age(agify.getAge())
                .ageProbability(agify.getProbability() != null ? agify.getProbability() : null)
                .country(!nationalize.getCountry().isEmpty() ? nationalize.getCountry().get(0).getCountry_id() : null)
                .countryProbability(!nationalize.getCountry().isEmpty() ? nationalize.getCountry().get(0).getProbability() : null)
                .build();
    }

    private void cacheResult(Person person) {
        cache.put(person.getFullName(), person);
    }

    private Person cachedPerson(Person person) {
        String name = person.getFullName();
        return cache.get(name);
    }
}
