package dk.persondata.GenderizeService;

import reactor.core.publisher.Mono;

public interface GenderizeService {
    Mono<GenderizeDTO> fetch(String name);
}
