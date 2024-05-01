package dk.persondata.person;

import reactor.core.publisher.Mono;

public interface PersonService {
    public Mono<Person> getPerson(String firstName, String middleName, String lastName);
}
