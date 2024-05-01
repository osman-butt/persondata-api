package dk.persondata.apiService;

import reactor.core.publisher.Mono;

public interface ApiService<T> {
    public Mono<T> fetch(String name);
}
