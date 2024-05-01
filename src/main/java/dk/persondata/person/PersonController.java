package dk.persondata.person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Mono<Person> getPerson(@RequestParam(name = "firstName", required = false) String firstName,
                                  @RequestParam(name = "middleName", required = false) String middleName,
                                  @RequestParam(name = "lastName", required = false) String lastName) {
        return personService.getPerson(firstName, middleName, lastName);
    }

}
