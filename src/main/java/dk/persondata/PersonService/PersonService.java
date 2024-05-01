package dk.persondata.PersonService;

import dk.persondata.AgifyService.AgifyDTO;
import dk.persondata.GenderizeService.GenderizeDTO;
import dk.persondata.NationalizeService.NationalizeDTO;
import dk.persondata.AgifyService.AgifyService;
import dk.persondata.GenderizeService.GenderizeService;
import dk.persondata.NationalizeService.NationalizeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {
    private final AgifyService agifyService;
    private final GenderizeService genderizeService;
    private final NationalizeService nationalizeService;
    private final Set<Person> cache = new HashSet<>();

    public PersonService(AgifyService agifyService,
                         GenderizeService genderizeService,
                         NationalizeService nationalizeService) {
        this.agifyService = agifyService;
        this.genderizeService = genderizeService;
        this.nationalizeService = nationalizeService;
    }

    public Mono<Person> getPerson(String name) {
        if (isCached(name)) {
            System.out.println("Returning cached result");
            Optional<Person> optionalPerson = cache.stream().filter(person -> person.getFullName().equals(name)).findFirst();
            if (optionalPerson.isPresent()) return Mono.just(optionalPerson.get());
        }
        Mono<AgifyDTO> agifyDTO = agifyService.fetch(name);
        Mono<GenderizeDTO> genderizeDTO = genderizeService.fetch(name);
        Mono<NationalizeDTO> nationalizeDTO = nationalizeService.fetch(name);
        return Mono.zip(agifyDTO, genderizeDTO, nationalizeDTO)
                .flatMap(tuple -> {
                    Person person = toEntity(tuple.getT1(), tuple.getT2(), tuple.getT3());
                    cacheResult(person);
                    return Mono.just(person);
                });
    }

    // From dtos to Person object
    private Person toEntity(AgifyDTO agify, GenderizeDTO genderize, NationalizeDTO nationalize) {
        return Person.builder()
                .fullName(agify.getName())
                .firstName(agify.getName())
                .middleName(agify.getName())
                .lastName(agify.getName())
                .gender(genderize.getGender())
                .genderProbability(genderize.getProbability())
                .age(agify.getAge())
                .ageProbability(null)
                .country(!nationalize.getCountry().isEmpty() ? nationalize.getCountry().get(0).getCountry_id() : null)
                .countryProbability(!nationalize.getCountry().isEmpty() ? nationalize.getCountry().get(0).getProbability() : null)
                .build();
    }

    private void cacheResult(Person person) {
        cache.add(person);
    }

    private boolean isCached(String name) {
        return cache.stream().anyMatch(person -> person.getFullName().equals(name));
    }

}
