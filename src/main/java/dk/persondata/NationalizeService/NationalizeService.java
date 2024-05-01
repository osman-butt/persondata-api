package dk.persondata.NationalizeService;

import reactor.core.publisher.Mono;

public interface NationalizeService {
    Mono<NationalizeDTO> fetch(String name);
}
