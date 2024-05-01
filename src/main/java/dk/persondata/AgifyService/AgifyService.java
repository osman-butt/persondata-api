package dk.persondata.AgifyService;

import reactor.core.publisher.Mono;

public interface AgifyService{
    Mono<AgifyDTO> fetch(String name);
}
